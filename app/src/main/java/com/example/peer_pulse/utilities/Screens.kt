package com.example.peer_pulse.utilities

import okhttp3.Route

sealed class Screens(val route: String){
    data object SplashScreen: Screens("SplashScreen")
    // Add Screen routes here
}
