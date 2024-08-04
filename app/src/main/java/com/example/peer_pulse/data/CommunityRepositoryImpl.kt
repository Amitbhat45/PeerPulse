package com.example.peer_pulse.data

import android.util.Log
import com.example.peer_pulse.domain.model.Community
import com.example.peer_pulse.domain.repository.CommunityRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val firestore : FirebaseFirestore
): CommunityRepository {
    override suspend fun getGeneralPostIdsByCollege(collegeCode: String): Flow<ResponseState<List<String>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostIdsByCommunity(communityId: String): Flow<ResponseState<List<String>>> {
        TODO("Not yet implemented")
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

}