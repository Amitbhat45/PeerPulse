package com.example.peer_pulse.domain.repository

import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface collegeRepository {
    suspend fun registerCollege(name : String,code :String) : Flow<ResponseState<Boolean>>
}