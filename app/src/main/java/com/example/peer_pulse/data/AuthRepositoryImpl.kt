package com.example.peer_pulse.data

import android.util.Log
import android.widget.Toast
import com.example.peer_pulse.domain.repository.AuthRepository
import com.example.peer_pulse.utilities.ResponseState
import com.example.peer_pulse.utilities.Screens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val auth : FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    private var operationSuccessful = false
    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }


    override suspend fun signUp(email: String, password: String): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val user = authResult.user
        if (user != null){
            user.sendEmailVerification().await()
            while (!user.isEmailVerified) {
                delay(2000)
                user.reload().await()
            }
                val userId = user.uid
                val userMap = hashMapOf(
                    "userId" to userId,
                    "email" to email
                )
                firestore.collection("users").document(userId).set(userMap).await()
                emit(ResponseState.Success(true))

        } else {
            emit(ResponseState.Error("User not created"))
        }
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }


    override suspend fun login(email: String, password: String): Flow<ResponseState<Boolean>> = flow{
        emit(ResponseState.Loading)
        auth.signInWithEmailAndPassword(email, password).await()
        emit(ResponseState.Success(true))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }

    override suspend fun signOut(): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        auth.signOut()
        emit(ResponseState.Success(true))
    }.catch {
        emit(ResponseState.Error(it.message ?: "An unexpected error occurred"))
    }

}