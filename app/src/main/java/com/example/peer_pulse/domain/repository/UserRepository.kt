package com.example.peer_pulse.domain.repository

import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun myPosts(userId : String) : Flow<ResponseState<List<String>>>
    suspend fun bookmarkedPosts(userId : String) : Flow<ResponseState<List<String>>>
    suspend fun followingPages(userId : String) : Flow<ResponseState<List<String>>>
}