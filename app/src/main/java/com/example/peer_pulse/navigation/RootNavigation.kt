package com.example.peer_pulse.navigation

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.peer_pulse.R
import com.example.peer_pulse.data.login.GoogleAuthUiClient
import com.example.peer_pulse.domain.model.colleges
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.presentation.LandingScreen
import com.example.peer_pulse.presentation.community.CommunityMessageScreen
import com.example.peer_pulse.presentation.community.CommunityScreen
import com.example.peer_pulse.presentation.community.CommunityViewModel
import com.example.peer_pulse.presentation.login.LoginScreen
import com.example.peer_pulse.presentation.main.MainScreen
import com.example.peer_pulse.presentation.postUI.AddPost
import com.example.peer_pulse.presentation.postUI.PostViewModel
import com.example.peer_pulse.presentation.postUI.postInsideView
import com.example.peer_pulse.presentation.preferences.PreferencePage
import com.example.peer_pulse.presentation.preferences.Preferences1
import com.example.peer_pulse.presentation.preferences.PreferencesViewModel
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigation(
    navHostController: NavHostController,
    authViewModel: AuthViewModel,
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext: Context,
    profileViewModel: ProfileViewModel,
    postViewModel: PostViewModel,
    preferencesViewModel: PreferencesViewModel,
    permissionGranted: MutableState<Boolean>,
    communityViewModel: CommunityViewModel
) {
    NavHost(
        navController = navHostController, startDestination = Screens.SplashScreen1.route
    ) {
        composable(route = Screens.SplashScreen1.route) {
            SplashScreen1(navController = navHostController, authViewModel = authViewModel)
        }
        navigation(
            startDestination = Screens.LandingScreen.route,
            route = Screens.AuthGraph.route,
        ) {
            composable(route = Screens.LandingScreen.route){
                LandingScreen(navController = navHostController)
            }
            composable(route = Screens.SignUpEmailScreen.route) {
                SignUpEmailScreen(navController = navHostController, authViewModel = authViewModel)
            }
            composable(route = Screens.SignUpPasswordScreen.route) {
                SignUpPasswordScreen(
                    navController = navHostController,
                    authViewModel = authViewModel
                )
            }
            composable(route = Screens.LoginScreen.route) {

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
                            navHostController.navigate(Screens.MainGraph.route){
                                popUpTo(Screens.AuthGraph.route){
                                    inclusive = true
                                }
                            }
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
                LoginScreen(
                    state = state,
                    onSignInClick = {
                        coroutineScope.launch {
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
                        }
                    },
                    navController = navHostController,
                    authViewModel = authViewModel
                )
            }
            composable(route = Screens.PreferenceScreen1.route){
                Preferences1(navController = navHostController,authViewModel)
            }
        }
        navigation(
            startDestination = Screens.MainScreen.route,
            route = Screens.MainGraph.route
        ){
            composable(Screens.AddPostScreen.route){
                val collegeLogo = colleges.find { it.name == authViewModel.college }?.logo ?: R.drawable.google_image
                val collegeName = colleges.find { it.name == authViewModel.college }?.name ?: ""
                AddPost(
                    navController = navHostController,
                    postViewModel = postViewModel,
                    permissionGranted = permissionGranted,
                    collegeLogo = collegeLogo,
                    collegeName = collegeName
                )
            }
            composable(
                Screens.PagesScreen.route,
                arguments = listOf(
                    navArgument("pageId"){type = NavType.StringType}
                )
            ){backStackEntry ->
                val pageId = backStackEntry.arguments?.getString("pageId") ?: ""
                PreferencePage(
                    preferenceId = pageId,
                    preferencesViewModel = preferencesViewModel,
                    postViewModel = postViewModel,
                    navController = navHostController,
                    )
            }
            composable(route = Screens.MainScreen.route){
                MainScreen(
                    authViewModel = authViewModel,
                    navController = navHostController,
                    postViewModel = postViewModel
                )
            }
            composable(
                route = Screens.PostViewScreen.route,
                arguments = listOf(
                    navArgument("title") { type = NavType.StringType },
                    navArgument("description") { type = NavType.StringType },
                    navArgument("likes") { type = NavType.IntType },
                    navArgument("timestamp") { type = NavType.LongType },
                    navArgument("preferences") { type = NavType.StringType } ,
                    //navArgument("imageUrl") { type = NavType.StringType }

                )
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val description = backStackEntry.arguments?.getString("description") ?: ""
                val likes = backStackEntry.arguments?.getInt("likes") ?: 0
                val timestamp = backStackEntry.arguments?.getLong("timestamp") ?: 0L
                val preferences = backStackEntry.arguments?.getString("preferences") ?: ""
                //val imageUrlString = backStackEntry.arguments?.getString("imageUrl") ?: ""
                //val imageUrl = imageUrlString.split(",").filter { it.isNotEmpty() }

                postInsideView(
                    title = title,
                    description = description,
                    likes = likes,
                    timestamp = timestamp,
                    preferences = preferences,
                    navHostController,
                    //imageUrl = imageUrl
                )
            }
        }
        navigation(
            startDestination = Screens.ProfileScreen.route,
            route = Screens.ProfileGraph.route
        ){
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
        navigation(
            startDestination = Screens.CommunityScreen.route,
            route = Screens.CommunityGraph.route
        ){
            composable(Screens.CommunityScreen.route){
                val collegeLogo = colleges.find { it.name == authViewModel.college }?.logo ?: R.drawable.about_vector
                val collegeName = colleges.find { it.name == authViewModel.college }?.name ?: ""
                val collegeCode = colleges.find { it.name == authViewModel.college }?.code ?: ""
                CommunityScreen(
                    navController = navHostController,
                    communityViewModel = communityViewModel,
                    collegeLogo = collegeLogo,
                    collegeName = collegeName,
                    collegeCode = collegeCode
                )
            }
            composable(
                Screens.CommunityTopicScreen.route,
                arguments = listOf(
                    navArgument("communityName"){type = NavType.StringType}
                )
            ){backstackEntry->
                val communityName = backstackEntry.arguments?.getString("communityName") ?: ""
                CommunityMessageScreen(
                  navController = navHostController,
                   communityViewModel = communityViewModel,
                    communityName = communityName,
                    collegeCode = colleges.find { it.name == authViewModel.college }?.code ?: "",
                    permissionGranted = permissionGranted,
                    userName = profileViewModel.userName
                )
            }
        }
    }
}