package com.example.peer_pulse.data

import com.example.peer_pulse.domain.model.Community
import com.example.peer_pulse.domain.repository.collegeRepository
import com.example.peer_pulse.presentation.community.CommunityMessageScreen
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
        val collegeMap = hashMapOf(
            "name" to name,
        )
        val communityList = listOf<Community>(
            Community(name = "College Events", description = "Events happening in your college"),
            Community(name = "Alumni", description = "Connect with your college alumni"),
            Community(name = "Coding Community", description = "Coding Community of your college"),
            Community(name = "Dance Community", description = "Dance Community of your college"),
            Community(name = "Music Community", description = "Music Community of your college"),
            Community(name = "Fashion Community", description = "Fashion Community of your college"),
            Community(name = "Mechanical Community", description = "Mechanical Community of your college"),
            Community(name = "Electrical Community", description = "Electrical Community of your college"),
            Community(name = "Civil Community", description = "Civil Community of your college"),
            Community(name = "Sports Community", description = "Sports Community of your college"),
        )
        firestore.collection("colleges").document(code).set(collegeMap).await()
        communityList.forEach {
            val communityMap = Community(it.name,it.description)
            firestore.collection("colleges").document(code).collection("communities").document(it.name).set(communityMap).await()
        }
        emit(ResponseState.Success(true))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }
}