package com.example.peer_pulse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.presentation.LandingScreen
import com.example.peer_pulse.presentation.signup.SignUpEmailScreen
import com.example.peer_pulse.presentation.signup.SignUpPasswordScreen
import com.example.peer_pulse.presentation.splashScreens.SplashScreen1
import com.example.peer_pulse.utilities.Screens

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    authViewModel: AuthViewModel
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
    }
}