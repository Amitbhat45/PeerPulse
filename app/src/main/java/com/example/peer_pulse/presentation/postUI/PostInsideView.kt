package com.example.peer_pulse.presentation.postUI

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.peer_pulse.R
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.domain.model.getPreferenceById
import com.example.peer_pulse.presentation.replies.ReplyComponent
import com.example.peer_pulse.presentation.signup.AuthTopBar
import com.example.peer_pulse.utilities.Screens
import com.example.peer_pulse.utilities.rememberImeState

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostInsideView(
    postId : String,
    title: String,
    description: String,
    likes: Int,
    timestamp: Long,
    preferences: String,
    navController: NavController,
    //imageUrl: List<String>?
    postViewModel: PostViewModel,
    collegeLogo : Int,
    collegeName : String
) {
    var reply by remember {
        mutableStateOf("")
    }
    val pref = getPreferenceById(preferences)
    Scaffold(
        topBar = {
            AuthTopBar(
                title = "Peer_Pulse",
                onBackClick = {
                    navController.navigateUp()
                },
                backClick = true
            )
        }
    ) {
//        val imeState = rememberImeState()
       val scrollState = rememberScrollState()
//
//        LaunchedEffect(key1 = imeState.value) {
//            if(imeState.value){
//                scrollState.scrollTo(scrollState.maxValue)
//            }
//        }
        Column(
            modifier = Modifier
                .padding(it)
               // .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                   .verticalScroll(scrollState)
                   .weight(9f)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (pref != null) {
                        Image(
                            painter = painterResource(id = pref.logo),
                            contentDescription = "topicimage",
                            Modifier
                                .size(35.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "topicimage",
                            Modifier
                                .size(35.dp)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "${preferences}", fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        Screens.PagesScreen.createRoute(
                                            preferences
                                        )
                                    ) {
                                        launchSingleTop = true
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = getTimeAgo(timestamp), fontSize = 10.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(0.dp))
                        Text(
                            text = "Clgname",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Column(Modifier.padding(start = 20.dp, end = 20.dp)) {
                    Text(
                        text = title,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,

                        )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = description,
                        fontSize = 16.sp,

                        )
                }
                /*if (imageUrl?.isNotEmpty() == true) {
                    Spacer(modifier = Modifier.width(8.dp))
                    AsyncImage(
                        model = imageUrl?.get(0),
                        contentDescription = "Post Image",
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(0.dp))

                        // placeholder = painterResource(R.drawable.ic_launcher_background),
                        // error = painterResource(R.drawable.ic_launcher_background)
                    )
                }*/

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "",
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(0.dp))
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Likes",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.likes_vector),
                            contentDescription = "likes",
                            Modifier
                                .size(20.dp)
                                .clickable { },
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Replies",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.comment_vector),
                            contentDescription = "replies",
                            Modifier.size(13.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Bookmark",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.bookmarks_vector),
                            contentDescription = "bookmark",
                            Modifier
                                .size(20.dp)
                                .clickable { },
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                ReplyComponent(
                    postViewModel = postViewModel,
                    postId = postId,
                )
            }

            Column(
                modifier = Modifier
                    //.weight(1f)
                    .imePadding()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Bottom
            ) {
                OutlinedTextField(
                    value = reply,
                    onValueChange = { reply = it },
                    placeholder = {
                        Text(
                            "Add a Reply",
                            fontSize = 14.sp,
                            color = Color.Gray,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                postViewModel.saveReply(
                                    postId,
                                    reply,
                                    collegeName,
                                    collegeLogo
                                )
                                reply = ""
                            },
                            enabled = reply.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                    /*modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp) // Standard height for text fields
                        .padding(horizontal = 16.dp), // Padding for proper alignment
                    colors = TextFieldDefaults.textFieldColors(
                        //backgroundColor = Color.White, // Set a solid background color if needed
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        //textColor = Color.Black // Ensure text color contrasts with the background
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp)*/
                )
            }
        }
    }
}