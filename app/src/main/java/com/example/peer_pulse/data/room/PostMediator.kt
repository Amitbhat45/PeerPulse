package com.example.peer_pulse.data.room

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await





@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val firestore: FirebaseFirestore,
    private val database: PostsDatabase,
    private val userPreferences: List<String>
) : RemoteMediator<Int, post>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, post>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.id
            }

            val query = firestore.collection("posts")
                .whereIn("preferences", userPreferences)
                .whereEqualTo("collegeCode","")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(state.config.pageSize.toLong())

            val snapshot = if (loadKey != null) {
                query.startAfter(loadKey).get().await()
            } else {
                query.get().await()
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

            //Log.d("PostRemoteMediator", "Number of posts retrieved: ${posts.size}")

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
}



