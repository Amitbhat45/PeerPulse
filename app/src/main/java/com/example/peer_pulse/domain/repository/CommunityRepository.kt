package com.example.peer_pulse.domain.repository

import androidx.paging.PagingData
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.model.Community
import com.example.peer_pulse.domain.model.Message
import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun getGeneralPostIdsByCollege(collegeName: String): Flow<ResponseState<List<String>>>
    suspend fun getAllCommunities(collegeCode: String): Flow<ResponseState<List<Community>>>
    suspend fun getMessagesByCommunity(collegeCode: String, communityId: String): Flow<ResponseState<List<Message>>>
    suspend fun sendMessage(text: String, imageUrl: String, userId: String, communityName: String, collegeCode: String, timeStamp: Long,userName : String): Flow<ResponseState<Boolean>>
    suspend fun deleteMessage(messageId : String) : Flow<ResponseState<Boolean>>
    suspend fun getPosts(preferences: List<String>,clgcode:String): Flow<PagingData<post>>

}