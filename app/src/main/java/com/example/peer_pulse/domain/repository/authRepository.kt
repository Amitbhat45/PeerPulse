package com.example.peer_pulse.domain.repository

import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(email: String, password: String,userName : String) : Flow<ResponseState<Boolean>>
    suspend fun login(email: String, password: String) : Flow<ResponseState<Boolean>>
    suspend fun signOut() : Flow<ResponseState<Boolean>>
    fun isUserAuthenticated () : Boolean
    suspend fun verifyUsername(userName : String) : Flow<ResponseState<Boolean>>
}