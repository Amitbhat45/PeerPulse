package com.example.peer_pulse.utilities

import okhttp3.Route

sealed class Screens(val route: String){
    data object SplashScreen1: Screens("SplashScreen1")
    data object SignUpEmailScreen: Screens("SignUpEmailScreen")
    data object SignUpPasswordScreen: Screens("SignUpPasswordScreen")
    data object LandingScreen: Screens("LandingScreen")
    data object LoginScreen:Screens("LoginScreen")
    data object MainScreen:Screens("MainScreen")

}
