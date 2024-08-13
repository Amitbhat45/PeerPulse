package com.example.peer_pulse.data

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.peer_pulse.data.room.PostRemoteMediator
import com.example.peer_pulse.data.room.PostsDatabase
import com.example.peer_pulse.data.room.TimeRange
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.domain.repository.PostsRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMostLikedPostsLastWeek(preferences: List<String>): Flow<PagingData<post>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = PostRemoteMediator(
                firestore = firestore,
                database = database,
                userPreferences = preferences,
                timeRange = TimeRange.LAST_WEEK,
                sortByLikes = true
            ),
            pagingSourceFactory = { database.postDao().getPosts(preferences) }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMostLikedPostsLastMonth(preferences: List<String>): Flow<PagingData<post>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = PostRemoteMediator(firestore, database, preferences, TimeRange.LAST_MONTH, sortByLikes = true),
            pagingSourceFactory = { database.postDao().getPosts(preferences) }
        ).flow
    }

   @OptIn(ExperimentalPagingApi::class)
   override suspend fun getMostLikedPostsLastYear(preferences: List<String>): Flow<PagingData<post>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = PostRemoteMediator(firestore, database, preferences, TimeRange.LAST_YEAR, sortByLikes = true),
            pagingSourceFactory = { database.postDao().getPosts(preferences) }
        ).flow
    }

    override suspend fun getRepliesId(postId: String): Flow<ResponseState<List<String>>> = callbackFlow<ResponseState<List<String>>> {
        ResponseState.Loading
        val snapshot = firestore.collection("posts").document(postId).collection("replies").get().await()
        val replyIds = snapshot.documents.map { it.id }
        ResponseState.Success(replyIds)
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }

    override suspend fun getReply(postId: String, replyId: String): Flow<ResponseState<String>> = callbackFlow<ResponseState<String>> {
        ResponseState.Loading
        val snapshot = firestore.collection("posts").document(postId).collection("replies").document(replyId).get().await()
        val reply = snapshot.toObject(String::class.java)
        ResponseState.Success(reply!!)
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }

  /*  suspend fun uploadImage(uri: Uri, context: Context): String? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = "${System.currentTimeMillis()}.jpg"  // Ensure unique file name
        val storageRef = FirebaseStorage.getInstance().reference.child("images/$fileName")


        return try {
            val uploadTask = storageRef.putStream(inputStream!!)
            val downloadUri = uploadTask.await().storage.downloadUrl.await()
            downloadUri.toString()  // Return the URL of the uploaded image
        } catch (e: Exception) {
            Log.e("UploadImage", "Error uploading image", e)
            null
        }
    }*/

    override suspend fun savePost(
        title: String,
        description: String,
        imageUris: List<Uri?>,
        preferences: String,
        preferencesId: String,
        userId: String
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val imageUrls = mutableListOf<String>()
            for (uri in imageUris) {
                val fileName = "${System.currentTimeMillis()}.jpg"
                val storageRef = FirebaseStorage.getInstance().reference.child("images/$fileName")
                val uploadTask = uri?.let { storageRef.putFile(it) }
                val downloadUrl = uploadTask?.await()?.storage?.downloadUrl?.await().toString()
                imageUrls.add(downloadUrl)
            }

            val postCollection = firestore.collection("posts")
            val id = postCollection.document().id
            val postDetails = hashMapOf(
                "id" to id,
                "userId" to userId,
                "title" to title,
                "description" to description,
                "images" to imageUrls, // Use the image URLs
                "timestamp" to System.currentTimeMillis(),
                "likes" to 0,
                "preferences" to preferences,
                "preferencesId" to preferencesId
            )

            postCollection.document(id).set(postDetails).await()

            emit(ResponseState.Success(true))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message ?: "An unexpected error occurred"))
        }
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

