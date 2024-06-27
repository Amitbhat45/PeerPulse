package com.example.peer_pulse.data

import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.domain.repository.UserRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore : FirebaseFirestore
): UserRepository {
    override suspend fun myPosts(userId: String): Flow<ResponseState<List<String>>> = callbackFlow{
        ResponseState.Loading
        val snapshotListener = firestore.collection("posts")
            .whereEqualTo("userId",userId)
            .addSnapshotListener { snapshot, error ->
                val response = if(snapshot != null){
                    val postIds = snapshot.documents.map { it.id }
                    ResponseState.Success(postIds)
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

    override suspend fun bookmarkedPosts(userId: String): Flow<ResponseState<List<String>>> = callbackFlow  {
        ResponseState.Loading
        val snapshotListener = firestore.collection("users")
            .document(userId)
            .addSnapshotListener { snapshot, error ->
                val response = if(snapshot != null){
                    val postIds = snapshot["bookmarks"] as List<String>
                    ResponseState.Success(postIds)
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

    override suspend fun followingPages(userId: String): Flow<ResponseState<List<String>>> = callbackFlow{
        ResponseState.Loading
        val snapshotListener = firestore.collection("users")
            .document(userId)
            .addSnapshotListener { snapshot, error ->
                val response = if(snapshot != null){
                    val postIds = snapshot["preferences"] as List<String>
                    ResponseState.Success(postIds)
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

}