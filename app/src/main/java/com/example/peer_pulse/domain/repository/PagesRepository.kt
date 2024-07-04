package com.example.peer_pulse.domain.repository

import com.example.peer_pulse.utilities.ResponseState
import kotlinx.coroutines.flow.Flow

interface PagesRepository {
    suspend fun getPostByPreference(preferenceId: String): Flow<ResponseState<List<String>>>
}
