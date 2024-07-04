package com.example.peer_pulse.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    suspend fun getPost(postId : String) : Flow<ResponseState<Post>>
    suspend fun getRepliesId(postId : String) : Flow<ResponseState<List<String>>>
    suspend fun getReply(postId: String,replyId : String) : Flow<ResponseState<String>>

    suspend fun savePost(
        title : String,
        description : String,
        images : List<String>,
        preferences : String,
        preferencesId : String,
        userId : String
    ):Flow<ResponseState<Boolean>>
    suspend fun deletePost(postId:String):Flow<ResponseState<String>>
    suspend fun getPosts(preferences: List<String>): Flow<PagingData<post>>
}