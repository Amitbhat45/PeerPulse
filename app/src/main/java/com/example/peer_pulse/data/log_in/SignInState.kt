package com.example.peer_pulse.data.log_in

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)