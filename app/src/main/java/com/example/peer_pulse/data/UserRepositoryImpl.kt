package com.example.peer_pulse.data

import android.util.Log
import com.example.peer_pulse.domain.repository.UserRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
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
                    Log.d("UserRepositoryImpl", snapshot["preferences"].toString())
                    val pageNames = snapshot["preferences"] as List<String>
                    ResponseState.Success(pageNames)
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

    override suspend fun updateFollowingPages(
        userId: String,
        followingPageNames: List<String?>
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        firestore.runTransaction { transaction ->
            val userDocRef = firestore.collection("users").document(userId)
            transaction.update(userDocRef, "preferences", followingPageNames)
        }.await()
        emit(ResponseState.Success(true))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }


}