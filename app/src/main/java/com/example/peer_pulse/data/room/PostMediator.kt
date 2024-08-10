package com.example.peer_pulse.data.room

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.Calendar


@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val firestore: FirebaseFirestore,
    private val database: PostsDatabase,
    private val userPreferences: List<String>,
    private val timeRange: TimeRange? = null,
    private val sortByLikes: Boolean = false
) : RemoteMediator<Int, post>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, post>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.let { listOf(it.likes, it.timestamp) }
            }

            var query = firestore.collection("posts")
                .whereIn("preferences", userPreferences)

            // Apply time filter
            val timeFilteredQuery = when (timeRange) {
                TimeRange.LAST_WEEK -> {
                   // Log.d("PostRemoteMediator", "Applying time filter: LAST_WEEK")
                    query.whereGreaterThan("timestamp", getLastWeekTimestamp())
                }
                TimeRange.LAST_MONTH -> {
                   // Log.d("PostRemoteMediator", "Applying time filter: LAST_MONTH")
                    query.whereGreaterThan("timestamp", getLastMonthTimestamp())
                }
                TimeRange.LAST_YEAR -> {
                   // Log.d("PostRemoteMediator", "Applying time filter: LAST_YEAR")
                    query.whereGreaterThan("timestamp", getLastYearTimestamp())
                }
                null -> {
                    //Log.d("PostRemoteMediator", "No time filter applied")
                    query
                }
            }

            // Apply ordering
            val finalQuery = if (sortByLikes) {
                timeFilteredQuery
                    .orderBy("likes", Query.Direction.DESCENDING)
                    .orderBy("timestamp", Query.Direction.DESCENDING) // Use a secondary order to maintain consistent results
            } else {
                timeFilteredQuery.orderBy("timestamp", Query.Direction.DESCENDING)
            }.limit(state.config.pageSize.toLong())

           // Log.d("PostRemoteMediator", "Final query: $finalQuery")

            val snapshot = if (loadKey != null) {
                finalQuery.startAfter(loadKey).get().await()
            } else {
                finalQuery.get().await()
            }

            val posts = snapshot.documents.map { document ->
                val post = document.toObject(post::class.java)
                if (post != null) {
                    val timestamp = document.get("timestamp")
                    val timestampLong = when (timestamp) {
                        is Timestamp -> timestamp.toDate().time
                        is Long -> timestamp
                        else -> throw IllegalStateException("Unexpected timestamp type: ${timestamp?.javaClass?.name}")
                    }
                    post.copy(timestamp = timestampLong)
                } else {
                    throw IllegalStateException("Failed to parse post from Firestore")
                }
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.postDao().clearPosts()
                }
                database.postDao().insertAll(posts)
            }
            MediatorResult.Success(endOfPaginationReached = posts.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getLastWeekTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.WEEK_OF_YEAR, -1)
        return calendar.timeInMillis
    }

    private fun getLastMonthTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        return calendar.timeInMillis
    }

    private fun getLastYearTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -1)
        return calendar.timeInMillis
    }
}

enum class TimeRange {
    LAST_WEEK,
    LAST_MONTH,
    LAST_YEAR
}





