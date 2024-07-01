package com.example.peer_pulse.utilities

import okhttp3.Route

sealed class Screens(val route: String){
    data object SplashScreen1: Screens("SplashScreen1")
    data object SignUpEmailScreen: Screens("SignUpEmailScreen")
    data object SignUpPasswordScreen: Screens("SignUpPasswordScreen")
    data object LandingScreen: Screens("LandingScreen")
    data object LoginScreen:Screens("LoginScreen")
    data object PreferenceScreen1:Screens("PreferenceScreen1")
    data object MainScreen:Screens("MainScreen")
    data object ProfileScreen:Screens("ProfileScreen")
    data object MyPostScreen:Screens("MyPostScreen")
    data object BookmarkedPostScreen:Screens("BookmarkedPostScreen")
    data object FollowingPageScreen:Screens("FollowingPageScreen")
    data object AddPostScreen:Screens("AddPostScreen")
    data object PagesScreen:Screens("pages/{pageId}"){
        fun createRoute(pageId: String): String{
            return "pages/$pageId"
        }
    }
}
