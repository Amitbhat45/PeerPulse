package com.example.peer_pulse.domain.repository

import androidx.paging.PagingData
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface PagesRepository {
    suspend fun getPostByPreference(preferenceId: String): Flow<ResponseState<List<String>>>

    suspend fun getPostbyTopic(preferences: List<String>): Flow<PagingData<post>>
    suspend fun getMostLikedPostsLastWeek(preferences: List<String>): Flow<PagingData<post>>
    suspend fun getMostLikedPostsLastMonth(preferences: List<String>): Flow<PagingData<post>>
    suspend fun  getMostLikedPostsLastYear(preferences: List<String>): Flow<PagingData<post>>
}
