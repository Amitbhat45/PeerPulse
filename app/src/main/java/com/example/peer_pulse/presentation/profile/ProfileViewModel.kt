package com.example.peer_pulse.presentation.profile

import androidx.lifecycle.ViewModel
import com.example.peer_pulse.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth : FirebaseAuth,
    private val userRepository: UserRepository
) : ViewModel() {
    init {
        auth.addAuthStateListener {
            userId = it.currentUser?.uid
        }
    }

    var userId = auth.currentUser?.uid
}