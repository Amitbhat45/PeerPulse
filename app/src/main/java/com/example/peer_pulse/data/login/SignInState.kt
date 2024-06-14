package com.example.peer_pulse.data.login

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)