package com.example.peer_pulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.peer_pulse.data.login.GoogleAuthUiClient
import com.example.peer_pulse.navigation.NavigationHost
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.presentation.postUI.PostViewModel
import com.example.peer_pulse.presentation.profile.ProfileViewModel
import com.example.peer_pulse.ui.theme.PeerPulseTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context=applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeerPulseTheme {
                Surface(modifier = Modifier.fillMaxSize() , color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val authViewModel : AuthViewModel = hiltViewModel()
                    val profileViewModel : ProfileViewModel = hiltViewModel()
                    val postViewModel : PostViewModel = hiltViewModel()
                    NavigationHost(
                        navHostController = navController,
                        authViewModel = authViewModel,
                        googleAuthUiClient = googleAuthUiClient,
                        applicationContext,
                        profileViewModel = profileViewModel,
                        postViewModel = postViewModel
                    )
                }
            }
        }
    }
}
