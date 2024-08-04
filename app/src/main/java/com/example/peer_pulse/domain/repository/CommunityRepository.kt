package com.example.peer_pulse.domain.repository

import com.example.peer_pulse.domain.model.Community
import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun getGeneralPostIdsByCollege(collegeCode: String): Flow<ResponseState<List<String>>>
    suspend fun getPostIdsByCommunity(communityId: String): Flow<ResponseState<List<String>>>
    suspend fun getAllCommunities(collegeCode: String): Flow<ResponseState<List<Community>>>
}