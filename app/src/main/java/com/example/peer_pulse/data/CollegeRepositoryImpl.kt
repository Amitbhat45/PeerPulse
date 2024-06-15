package com.example.peer_pulse.data

import com.example.peer_pulse.domain.repository.collegeRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CollegeRepositoryImpl @Inject constructor(
    private val firestore : FirebaseFirestore
): collegeRepository {
    override suspend fun registerCollege(name: String,code : String): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        firestore.collection("colleges").document(code).set(hashMapOf("name" to name)).await()
        emit(ResponseState.Success(true))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }
}