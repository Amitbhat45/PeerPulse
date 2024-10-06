package com.example.peer_pulse.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.peer_pulse.data.room.PostRemoteMediator
import com.example.peer_pulse.data.room.PostRemoteMediator2
import com.example.peer_pulse.data.room.PostsDatabase
import com.example.peer_pulse.data.room.TimeRange
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.repository.PagesRepository
import com.example.peer_pulse.utilities.ResponseState
import com.example.peer_pulse.utilities.TimeUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class PagesRepositoryImpl @Inject constructor(
    private val firestore : FirebaseFirestore,
    private val database: PostsDatabase
): PagesRepository {
    override suspend fun getPostByPreference(preferenceId: String): Flow<ResponseState<List<String>>> = callbackFlow {
       ResponseState.Loading
        val snapshot = firestore.collection("posts").whereEqualTo("preferencesId", preferenceId)
            .addSnapshotListener { snapshot, error ->
            val response = if(snapshot != null){
                val postIds = snapshot.documents.map { it.id }
                ResponseState.Success(postIds)
            }
            else{
                ResponseState.Error(error?.message ?: "An unexpected error occurred")
            }
                trySend(response).isSuccess
        }
        awaitClose {
            snapshot.remove()
        }
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPostbyTopic(preferences: List<String>): Flow<PagingData<post>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = PostRemoteMediator(firestore, database, preferences),
            pagingSourceFactory = { database.postDao().getPosts(preferences) }
        ).flow
    }
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMostLikedPostsLastWeek(preferences: List<String>): Flow<PagingData<post>> {
        val lastweek=TimeUtils.getLastWeekTimestamp()
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = PostRemoteMediator2(
                firestore = firestore,
                database = database,
                userPreferences = preferences,
                timeRange = TimeRange.LAST_WEEK,
            ),
            pagingSourceFactory = { database.postDao().getPostbyLastweek(preferences,lastweek) }
        ).flow
    }
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMostLikedPostsLastMonth(preferences: List<String>): Flow<PagingData<post>> {
        val lastmonth=TimeUtils.getLastMonthTimestamp()
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = PostRemoteMediator2(firestore, database, preferences, TimeRange.LAST_MONTH),
            pagingSourceFactory = { database.postDao().getPostbyLastmonth(preferences,lastmonth) }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMostLikedPostsLastYear(preferences: List<String>): Flow<PagingData<post>> {
        val lastyear=TimeUtils.getLastYearTimestamp()
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = PostRemoteMediator2(firestore, database, preferences, TimeRange.LAST_YEAR),
            pagingSourceFactory = { database.postDao().getPostbyLastyear(preferences,lastyear) }
        ).flow
    }
}