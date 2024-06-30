package com.example.peer_pulse.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.presentation.postUI.PostViewModel
import com.example.peer_pulse.utilities.Screens

@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    postViewModel: PostViewModel
){
    Scaffold(
        bottomBar = {
            BottomNavigation(
                selectedButton = BottomNavigationScreens.Main,
                navController = navController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddPostScreen.route)
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription =null )
            }
        }
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Text(
                text = authViewModel.college,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            Button(onClick = {
                val postDetails = Post(

                    userId = authViewModel.userId ?: "unknown_user",
                    title = "Sample Post",
                    description = "This is a sample post",
                    imageUrl = listOf(""),
                    timestamp = "",
                    likes = 0,
                    preferences = "Sample Preferences",
                    preferenceId = "pref123"
                )
                postViewModel.savePost(postDetails)
            }) {
                Text("Save Post")
            }
        }
    }
}