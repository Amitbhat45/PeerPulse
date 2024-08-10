package com.example.peer_pulse.utilities

import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.model.Post
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
    data object CollegePagesScreen:Screens("collegePages/{pageId}") {
        fun createRoute(pageId: String): String {
            return "collegePages/$pageId"
        }
    }
    data object PostViewScreen : Screens("post/{title}/{description}/{likes}/{timestamp}/{preferences}") {
        fun postdetails(post: post): String {
            return "post/${post.title}/${post.description}/${post.likes}/${post.timestamp}/${post.preferences}"
        }
    }
    data object CommunityScreen : Screens("CommunityScreen")
    data object CommunityTopicScreen : Screens("CommunityTopicScreen/{communityName}"){
        fun createRoute(communityName: String): String {
            return "CommunityTopicScreen/$communityName"
        }
    }

    // Graph Routes
    data object AuthGraph: Screens("AuthGraph")
    data object MainGraph: Screens("MainGraph")
    data object ProfileGraph: Screens("ProfileGraph")
    data object PostGraph: Screens("PostGraph")
    data object PreferencesGraph: Screens("PreferencesGraph")
    data object CommunityGraph: Screens("CommunityGraph")
}
