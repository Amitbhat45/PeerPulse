package com.example.peer_pulse.navigation

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.peer_pulse.data.log_in.GoogleAuthUiClient
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.presentation.LandingScreen
import com.example.peer_pulse.presentation.MainScreen
import com.example.peer_pulse.presentation.log_in.LoginScreen
import com.example.peer_pulse.presentation.signup.SignUpEmailScreen
import com.example.peer_pulse.presentation.signup.SignUpPasswordScreen
import com.example.peer_pulse.presentation.splashScreens.SplashScreen1
import com.example.peer_pulse.utilities.Screens
import kotlinx.coroutines.launch

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    authViewModel: AuthViewModel,
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext:Context
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.SplashScreen1.route
    ) {
        composable(route = Screens.SignUpEmailScreen.route){
            SignUpEmailScreen(navController = navHostController, authViewModel = authViewModel)
        }
        composable(route = Screens.SignUpPasswordScreen.route){
            SignUpPasswordScreen(navController = navHostController, authViewModel = authViewModel)
        }
        composable(route = Screens.SplashScreen1.route){
            SplashScreen1(navController = navHostController, authViewModel = authViewModel)
        }
        composable(route = Screens.LandingScreen.route){
            LandingScreen(navController = navHostController)
        }
        composable(route=Screens.LoginScreen.route){

            val state by authViewModel.state.collectAsState()
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null) {
                    navHostController.navigate(Screens.MainScreen.route)
                }
            }
            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navHostController.navigate(Screens.MainScreen.route)
                    authViewModel.resetState()
                }
            }
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    Log.d("MainActivity", "Sign-in result: ${result.resultCode}")
                    if (result.resultCode == Activity.RESULT_OK) {
                        coroutineScope.launch {
                            try {
                                val signInResult =
                                    googleAuthUiClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                authViewModel.onSignInResult(signInResult)
                            } catch (e: Exception) {
                                Log.e(
                                    "MainActivity",
                                    "Error processing sign-in result",
                                    e
                                )
                            }
                        }
                    }
                }
            )
            LoginScreen(state = state, onSignInClick = {  coroutineScope.launch {
                val signInIntentSender = googleAuthUiClient.signIn()
                if (signInIntentSender != null) {
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender
                        ).build()
                    )
                } else {
                    Log.d("Login1", "Sign-in intent sender is null.")
                }
            } }, navController = navHostController,authViewModel = authViewModel)
        }
        composable(route = Screens.MainScreen.route){
            MainScreen()
        }
    }
}