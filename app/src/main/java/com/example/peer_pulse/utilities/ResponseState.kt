package com.example.peer_pulse.utilities

sealed class ResponseState <out T> {
    data class Success<T>(var data:T) : ResponseState<T>()
    data class Error<T>(val message: String) : ResponseState<T>()
    data object Loading : ResponseState<Nothing>()
}