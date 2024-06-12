package com.example.peer_pulse.domain.repository

import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(email: String, password: String) : Flow<ResponseState<Boolean>>
    fun isUserAuthenticated () : Boolean
}