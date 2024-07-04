package com.example.peer_pulse.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.peer_pulse.presentation.postUI.PostCard
import com.example.peer_pulse.presentation.postUI.PostViewModel
import com.example.peer_pulse.presentation.signup.AuthTopBar
import com.example.peer_pulse.utilities.ResponseState

@Composable
fun BookmarkedPostsScreen(
    navController : NavController,
    profileViewModel : ProfileViewModel,
    postViewModel: PostViewModel
) {
    Scaffold(
        topBar = {
            AuthTopBar(
                title = "Bookmarked Posts",
                backClick = true,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    ) {
        when (val response = profileViewModel.state2.value) {
            is ResponseState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = response.message,
                        fontSize = 32.sp
                    )
                }
            }

            ResponseState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize()
                )
            }

            is ResponseState.Success -> {
                if (response.data != null) {
                    if (profileViewModel.bookmarkedPostIds.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(it),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No Posts Yet",
                                fontSize = 32.sp
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(it)
                        ) {
                            items(profileViewModel.bookmarkedPostIds.size) {
                                profileViewModel.postIds[it]?.let { it1 ->
                                    PostCard(
                                        id = it1,
                                        postViewModel = postViewModel
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "You have not posted anything yet.",
                            fontSize = 32.sp
                        )
                    }
                }
            }
        }
    }
}