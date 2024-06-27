package com.example.peer_pulse.domain.repository

import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    suspend fun getPost(postId : String) : Flow<ResponseState<Post>>
    suspend fun getRepliesId(postId : String) : Flow<ResponseState<List<String>>>
    suspend fun getReply(postId: String,replyId : String) : Flow<ResponseState<String>>
}