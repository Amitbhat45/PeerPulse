package com.example.peer_pulse.data

import android.util.Log
import com.example.peer_pulse.domain.model.Community
import com.example.peer_pulse.domain.model.Message
import com.example.peer_pulse.domain.repository.CommunityRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val firestore : FirebaseFirestore
): CommunityRepository {
    override suspend fun getGeneralPostIdsByCollege(collegeName: String): Flow<ResponseState<List<String>>> = callbackFlow{
        ResponseState.Loading
        val snapshotListener = firestore.collection("posts").whereEqualTo("collegeCode",collegeName)
            .addSnapshotListener{snapshot,error ->
            val response = if(snapshot != null){
                val posts = snapshot.documents.map { it.id }
                ResponseState.Success(posts)
            }
            else{
                ResponseState.Error(error?.message ?: "An unexpected error occurred")
            }
                trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun getAllCommunities(collegeCode: String): Flow<ResponseState<List<Community>>> = callbackFlow{
        ResponseState.Loading
        val snapshotListener = firestore.collection("colleges").document(collegeCode).collection("communities")
            .addSnapshotListener { snapshot, error ->
                val response = if(snapshot != null){
                    val communities = snapshot.documents.map { it.id }.map { comm -> Community(comm) }
                    ResponseState.Success(communities)
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

    override suspend fun getMessagesByCommunity(
        collegeCode: String,
        communityId: String
    ): Flow<ResponseState<List<Message>>> = callbackFlow{
        ResponseState.Loading
        val snapshotListener = firestore.collection("messages")
            .whereEqualTo("communityName", communityId)
            .whereEqualTo("collegeCode",collegeCode)
            .orderBy("timeStamp")
            .addSnapshotListener { snapshot, error ->
            val response = if(snapshot != null){
                val messages = snapshot.documents.map { it.toObject(Message::class.java)!! }
                ResponseState.Success(messages)
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

    override suspend fun sendMessage(
        text : String,
        imageUrl : String,
        userId : String,
        communityName: String,
        collegeCode : String,
        timeStamp : Long,
        userName : String
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        val id = firestore.collection("messages").document().id
        val messageMap = Message(
            text = text,
            imageUrl = imageUrl,
            userId = userId,
            communityName = communityName,
            collegeCode = collegeCode,
            timeStamp = timeStamp,
            messageId = id,
            userName = userName
        )
        firestore.collection("messages").document(id).set(messageMap).await()
        emit(ResponseState.Success(true))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }

    override suspend fun deleteMessage(messageId: String): Flow<ResponseState<Boolean>> = flow{
        emit(ResponseState.Loading)
        firestore.collection("messages").document(messageId).delete().await()
        emit(ResponseState.Success(true))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }

}