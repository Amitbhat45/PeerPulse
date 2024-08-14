package com.example.peer_pulse.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.peer_pulse.data.room.PostRemoteMediator
import com.example.peer_pulse.data.room.PostsDatabase
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.domain.model.Reply
import com.example.peer_pulse.domain.repository.PostsRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val firestore : FirebaseFirestore,
    private val database: PostsDatabase
) : PostsRepository {

    override suspend fun getPost(postId: String): Flow<ResponseState<Post>>  = callbackFlow{
        ResponseState.Loading
        val snapshot = firestore.collection("posts").document(postId).addSnapshotListener { snapshot, error ->
            val response = if(snapshot != null){
                val post = snapshot.toObject(Post::class.java)
                ResponseState.Success(post!!)
            }
            else{
                ResponseState.Error(error?.message ?: "An unexpected error occurred")
            }
            trySend(response).isSuccess

        }
        awaitClose{
            snapshot.remove()
        }

    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPosts(preferences: List<String>): Flow<PagingData<post>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = PostRemoteMediator(firestore, database, preferences),
            pagingSourceFactory = { database.postDao().getPosts(preferences) }
        ).flow
    }

    override suspend fun saveReply(
        postId: String,
        reply: String,
        userId: String,
        college: String,
        collegeLogo: Int
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        val id = firestore.collection("posts").document(postId).collection("replies").document().id
        val replyDetails = Reply(
            id = id,
            content = reply,
            userId = userId,
            postId = postId,
            timeStamp = System.currentTimeMillis(),
            college = college,
            collegeLogo = collegeLogo,
            likes = 0
        )
        firestore.collection("posts").document(postId).collection("replies").document(id).set(replyDetails).await()
        emit(ResponseState.Success(true))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }

    override suspend fun getRepliesId(postId: String): Flow<ResponseState<List<Reply>>> = callbackFlow<ResponseState<List<Reply>>> {
        ResponseState.Loading
        val snapshotListener = firestore.collection("posts").document(postId).collection("replies").addSnapshotListener { snapshot, error ->
            val response = if(snapshot != null){
                val replies = snapshot.documents.map { it.toObject(Reply::class.java)!! }
                ResponseState.Success(replies)
            }
            else{
                ResponseState.Error(error?.message ?: "An unexpected error occurred")
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }

    override suspend fun savePost(
        title: String,
        description: String,
        images: List<String>,
        preferences: String,
        preferencesId: String,
        userId: String,
        collegeCode: String,
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        val postCollection = firestore.collection("posts")
        val id = postCollection.document().id
        val postDetails = Post(
            id = id,
            title = title,
            description = description,
            imageUrl = images,
            preferences = preferences,
            preferenceId = preferencesId,
            userId = userId,
            timestamp = System.currentTimeMillis(),
            collegeCode = collegeCode,
        )

        postCollection.document(id).set(postDetails).await()
        emit(ResponseState.Success(true))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }


    override suspend fun deletePost(postId: String): Flow<ResponseState<String>> = flow {
        emit(ResponseState.Loading)
        val postCollection = firestore.collection("posts")
        try {
            postCollection.document(postId).delete().await()
            emit(ResponseState.Success(postId))
        } catch (e: Exception) {
            emit(ResponseState.Error("Error deleting post: ${e.message}"))
        }
    }



}

