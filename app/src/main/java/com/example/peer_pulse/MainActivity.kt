package com.example.peer_pulse

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.peer_pulse.data.login.GoogleAuthUiClient
import com.example.peer_pulse.navigation.NavigationHost
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.presentation.postUI.PostViewModel
import com.example.peer_pulse.presentation.preferences.PreferencesViewModel
import com.example.peer_pulse.presentation.profile.ProfileViewModel
import com.example.peer_pulse.ui.theme.PeerPulseTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity(), PermissionCallback{
    private var permissionGranted = mutableStateOf(false)
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context=applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 100
        const val REQUEST_READ_MEDIA_IMAGES = 101
        const val REQUEST_READ_MEDIA_VISUAL_USER_SELECTED = 102
    }
    @RequiresApi(Build.VERSION_CODES.O)
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
                    val preferencesViewModel : PreferencesViewModel = hiltViewModel()
                    NavigationHost(
                        navHostController = navController,
                        authViewModel = authViewModel,
                        googleAuthUiClient = googleAuthUiClient,
                        applicationContext,
                        profileViewModel = profileViewModel,
                        postViewModel = postViewModel,
                        preferencesViewModel = preferencesViewModel,
                        permissionGranted = permissionGranted
                    )
                }
            }
        }}
    override fun onPermissionGranted() {
        permissionGranted.value= true
    }

    override fun onPermissionDenied() {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE, REQUEST_READ_MEDIA_IMAGES, REQUEST_READ_MEDIA_VISUAL_USER_SELECTED -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Trigger permission granted state
                    (this as ComponentActivity).runOnUiThread {
                        onPermissionGranted()
                    }
                } else {
                    onPermissionDenied()
                }
            }
        }
    }
}
