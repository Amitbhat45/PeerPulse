package com.example.peer_pulse.presentation.postUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.example.peer_pulse.presentation.signup.AuthTopBar
import com.example.peer_pulse.utilities.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun postInsideView(
    title: String,
    description: String,
    likes: Int,
    timestamp: String,
    preferences: String,
    navController: NavController,
    //imageUrl: List<String>?
){
    var Reply by remember {
        mutableStateOf("")
    }
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
        Column(modifier = Modifier.padding(it)) {
        Column(
            Modifier
                .weight(9f)
                .verticalScroll(rememberScrollState())) {
            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,){
                Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription ="topicimage" ,
                    Modifier
                        .size(35.dp)
                        .clip(CircleShape))
                Spacer(modifier = Modifier.width(5.dp))
                Column(Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "${preferences}" ,fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { navController.navigate(Screens.PagesScreen.createRoute(preferences)){
                                launchSingleTop = true
                            } }
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "${timestamp}",fontSize = 10.sp, color= Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(0.dp))
                    Text(text = "Clgname",
                        fontSize = 10.sp,
                        color= Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Column(Modifier.padding(start=20.dp,end=20.dp)) {
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

            Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "",
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(0.dp)))
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement =  Arrangement.spacedBy(130.dp, alignment = Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.likes_vector), contentDescription = "likes",
                    Modifier
                        .size(20.dp)
                        .clickable { },colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray))
                Image(painter = painterResource(id = R.drawable.comment_vector), contentDescription = "replys",Modifier.size(13.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray))
                Image(painter = painterResource(id = R.drawable.bookmarks_vector), contentDescription = "bookmark",
                    Modifier
                        .size(20.dp)
                        .clickable { },
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray))
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "No Replies Yet",
                    fontSize = 20.sp,
                    fontWeight = Bold,
                    )
            }
        }

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom
            ){
                OutlinedTextField(
                    value = Reply,
                    onValueChange = { Reply = it },
                    placeholder = {
                        Text(
                            "Add a Reply",
                            fontSize = 14.sp,
                            color = Color.Gray ,
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
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