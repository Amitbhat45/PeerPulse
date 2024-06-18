package com.example.peer_pulse.presentation.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth : FirebaseAuth,
) : ViewModel() {
    init {
        auth.addAuthStateListener {
            userId = it.currentUser?.uid
        }
    }

    var userId = auth.currentUser?.uid
}