package com.example.peer_pulse.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.peer_pulse.R
import com.example.peer_pulse.domain.model.preferences
import com.example.peer_pulse.presentation.postUI.PostCard
import com.example.peer_pulse.presentation.signup.AuthTopBar
import com.example.peer_pulse.utilities.ResponseState
import com.example.peer_pulse.utilities.ToastMessage

@Composable
fun FollowingPagesScreen(
    navController : NavController,
    profileViewModel : ProfileViewModel
) {
    Scaffold(
       topBar = {
           AuthTopBar(
               title = "Following Pages",
               backClick = true,
               onBackClick = {
                   navController.navigateUp()
               }
           )
       }
    ){
        when(val response = profileViewModel.state3.value){
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
                    if (profileViewModel.followingPageIds.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No pages Yet",
                                fontSize = 32.sp
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(preferences.size) { index ->
                                FollowingPageRow(
                                    name = preferences[index],
                                    following = profileViewModel.followingPageIds.contains(preferences[index]),
                                    profileViewModel = profileViewModel
                                )

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
                            text = "You have not followed any page yet.",
                            fontSize = 32.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FollowingPageRow(
    name : String,
    following : Boolean,
    profileViewModel: ProfileViewModel
){
    var follow = remember{ mutableStateOf(following)}
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.google_image),
            contentDescription = "page image",
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape)
                .padding(4.dp)
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = name,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(8.dp)
                .weight(6f)
                .align(Alignment.CenterVertically),
        )
        Button(
            onClick = {
                if(follow.value){
                    follow.value = false
                    profileViewModel.removeFromFollowingPage(name)
                }
                else{
                    follow.value = true
                    profileViewModel.addToFollowingPage(name)
                }
            },
            modifier = Modifier
                .padding(4.dp)
                .weight(3f)
                .size(width = 24.dp, height = 40.dp)
                .align(Alignment.CenterVertically),
        ) {
            Text(
                text = if(follow.value) "Unfollow" else "Follow"
            )
        }

        when(val response = profileViewModel.state4.value){
            is ResponseState.Error -> {
                ToastMessage(response.message)
            }
            ResponseState.Loading -> {

            }
            is ResponseState.Success -> {
                if(response.data != null){
                    ToastMessage(
                        message = if(follow.value)
                            "You have followed the page"
                        else
                            "You have unfollowed the page"
                    )
                }
                else{

                }
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun FollowingPagesScreenPreview() {
//    FollowingPageRow(name = "Artificial Intelligence")
//}