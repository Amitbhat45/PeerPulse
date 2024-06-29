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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.peer_pulse.data.login.GoogleAuthUiClient
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.presentation.LandingScreen
import com.example.peer_pulse.presentation.main.MainScreen
import com.example.peer_pulse.presentation.login.LoginScreen
import com.example.peer_pulse.presentation.postUI.PostViewModel
import com.example.peer_pulse.presentation.preferences.Preferences1
import com.example.peer_pulse.presentation.profile.BookmarkedPostsScreen
import com.example.peer_pulse.presentation.profile.FollowingPagesScreen
import com.example.peer_pulse.presentation.profile.MyPostsScreen
import com.example.peer_pulse.presentation.profile.ProfileScreen
import com.example.peer_pulse.presentation.profile.ProfileViewModel
import com.example.peer_pulse.presentation.signup.SignUpEmailScreen
import com.example.peer_pulse.presentation.signup.SignUpPasswordScreen
import com.example.peer_pulse.presentation.splashScreens.SplashScreen1
import com.example.peer_pulse.utilities.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    authViewModel: AuthViewModel,
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext:Context,
    profileViewModel: ProfileViewModel,
    postViewModel : PostViewModel
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
        composable(route=Screens.LoginScreen.route) {

            val state by authViewModel.state.collectAsState()
            val coroutineScope = rememberCoroutineScope()
            /*  LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null) {
                    navHostController.navigate(Screens.MainScreen.route)
                }
            }*/LaunchedEffect(key1 = state.isSignInSuccessful) {
            if (state.isSignInSuccessful) {
                val userEmail = googleAuthUiClient.getSignedInUser()?.email
                if (userEmail != null) {
                    val isEmailFound = withContext(Dispatchers.IO) {
                        authViewModel.check(userEmail)
                    }
                    if (!isEmailFound) {
                        Toast.makeText(
                            applicationContext,
                            "Sign in successful",
                            Toast.LENGTH_LONG
                        ).show()
                        authViewModel.college = authViewModel.whichCollege(userEmail)
                        navHostController.navigate(Screens.MainScreen.route)
                        authViewModel.resetState()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "You need to Sign Up first",
                            Toast.LENGTH_LONG
                        ).show()
                        authViewModel.resetState()
                    }
                } else {
                    Log.e("SignInError", "Unable to get signed-in user email")
                }
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
        composable(route = Screens.PreferenceScreen1.route){
            Preferences1(navController = navHostController,authViewModel)
        }
        composable(route = Screens.MainScreen.route){
            MainScreen(authViewModel = authViewModel, navController = navHostController,postViewModel)
        }
        composable(Screens.ProfileScreen.route){
            ProfileScreen(
                navController = navHostController,
                profileViewModel = profileViewModel,
                authViewModel = authViewModel
            )
        }
        composable(Screens.MyPostScreen.route){
            MyPostsScreen(
                navController = navHostController,
                profileViewModel = profileViewModel,
                postViewModel = postViewModel
            )
        }
        composable(Screens.BookmarkedPostScreen.route){
            BookmarkedPostsScreen(
                navController = navHostController,
                profileViewModel = profileViewModel,
                postViewModel = postViewModel
            )
        }
        composable(Screens.FollowingPageScreen.route){
            FollowingPagesScreen(
                navController = navHostController,
                profileViewModel = profileViewModel
            )
        }
    }
}