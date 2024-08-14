package com.example.peer_pulse.utilities

import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.model.Post
import com.google.gson.Gson
import okhttp3.Route
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
    data object CollegePagesScreen:Screens("collegePages/{pageId}") {
        fun createRoute(pageId: String): String {
            return "collegePages/$pageId"
        }
    }
    object PostViewScreen : Screens("post_detail/{postJson}") {
        fun createRoute(post: post): String {
            val postJson = Gson().toJson(post)
            val encodedPostJson = URLEncoder.encode(postJson, StandardCharsets.UTF_8.toString())
            return "post_detail/$encodedPostJson"
        }
    }

    data object CommunityScreen : Screens("CommunityScreen")
}
