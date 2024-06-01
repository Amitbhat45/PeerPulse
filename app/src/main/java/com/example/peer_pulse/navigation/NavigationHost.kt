package com.example.peer_pulse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.peer_pulse.utilities.Screens

@Composable
fun NavigationHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.SplashScreen.route
    ) {
        // Add routes here
    }
}