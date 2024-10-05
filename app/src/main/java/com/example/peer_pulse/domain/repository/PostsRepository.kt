package com.example.peer_pulse.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.domain.model.Reply
import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    suspend fun getPost(postId : String) : Flow<ResponseState<Post>>
    suspend fun getRepliesId(postId : String) : Flow<ResponseState<List<Reply>>>

    suspend fun savePost(
        title: String,
        description: String,
        imageUris: List<Uri?>,
        preferences: String,
        preferencesId: String,
        userId: String,
        collegeCode: String,
    ):Flow<ResponseState<Boolean>>
    suspend fun deletePost(postId:String):Flow<ResponseState<String>>
    suspend fun getPosts(preferences: List<String>): Flow<ResponseState<PagingData<post>>>
    suspend fun saveReply(
        postId: String,
        reply: String,
        userId: String,
        college: String,
        collegeLogo: Int
    ): Flow<ResponseState<Boolean>>
    suspend fun getMostLikedPostsLastWeek(preferences: List<String>): Flow<PagingData<post>>
    suspend fun getMostLikedPostsLastMonth(preferences: List<String>): Flow<PagingData<post>>
    suspend fun  getMostLikedPostsLastYear(preferences: List<String>): Flow<PagingData<post>>
    suspend fun likePost(postId: String, userId: String): Flow<ResponseState<Boolean>>
}