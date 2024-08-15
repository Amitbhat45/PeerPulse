package com.example.peer_pulse.presentation.postUI

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.utilities.ResponseState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostCard(
    id : String,
    postViewModel: PostViewModel,
    navController: NavController
) {
    postViewModel.getPost(id)
    when (val response = postViewModel.postState.value) {
        is ResponseState.Error -> {
            Card(
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = response.message,
                        fontSize = 32.sp
                    )
                }
            }
        }

        ResponseState.Loading -> {
            Card {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is ResponseState.Success -> {
            if (response.data != null) {
                val postDetails = response.data
                if (postDetails != null) {
                   PostUI(
                       post = post(
                            id = postDetails.id,
                            userId = postDetails.userId,
                            title = postDetails.title,
                            description = postDetails.description,
                            images = postDetails.imageUrl,
                            timestamp = postDetails.timestamp,
                            likes = postDetails.likes,
                            preferences = postDetails.preferences,
                            preferencesId = postDetails.preferenceId
                       ) ,
                       navController = navController,
                          postViewModel = postViewModel
                   )
                }
            }
        }
    }
}