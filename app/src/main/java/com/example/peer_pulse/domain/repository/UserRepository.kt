package com.example.peer_pulse.domain.repository

import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun myPosts(userId : String) : Flow<ResponseState<List<String>>>
    suspend fun bookmarkedPosts(userId : String) : Flow<ResponseState<List<String>>>
    suspend fun followingPages(userId : String) : Flow<ResponseState<List<String>>>
    suspend fun updateFollowingPages(userId: String, followingPageNames: List<String?>) : Flow<ResponseState<Boolean>>
    suspend fun updateUsername(userId: String, username: String) : Flow<ResponseState<Boolean>>
    suspend fun getUsername(userId: String) : Flow<ResponseState<String>>
}