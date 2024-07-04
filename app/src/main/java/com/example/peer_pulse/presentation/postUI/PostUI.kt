package com.example.peer_pulse.presentation.postUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.peer_pulse.R
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.utilities.Screens

@Composable
fun PostUI(
    post: post,
    navController: NavController
){
    Card(onClick = { navController.navigate(Screens.PostViewScreen.postdetails(post)) {
        launchSingleTop = true
    } },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(0.dp)

    ) {
        /*val postdetail = post(
            id = "1",
            userId = "user1",
            title = post.title,
            description = post.description,
            images = post.images,
            timestamp = post.timestamp,
            likes = 10,
            preferences = post.preferences,
            preferencesId = "pref1"
        )*/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
        ){
            Column (
                Modifier
                    .fillMaxSize()
                    .padding(15.dp)){
                Row (Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription ="topicimage" ,
                        Modifier
                            .size(35.dp)
                            .clip(CircleShape))
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "${post.preferences}" ,fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { navController.navigate(Screens.PagesScreen.createRoute(post.preferences)){
                                    launchSingleTop = true
                                } }
                                )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "${post.timestamp}",fontSize = 10.sp, color=Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(0.dp))
                        Text(text = "Clgname",
                            fontSize = 10.sp,
                            color=Color.Gray
                            //fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth()) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "${post.title}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${post.description}",
                            fontSize = 14.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    if (post.images.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        AsyncImage(
                            model = post.images[0],
                            contentDescription = "Post Image",
                            modifier = Modifier
                                .height(70.dp).width(70.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .align(Alignment.Top),
                           // placeholder = painterResource(R.drawable.ic_launcher_background),
                           // error = painterResource(R.drawable.ic_launcher_background)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                    horizontalArrangement =  Arrangement.spacedBy(130.dp, alignment = Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.likes_vector), contentDescription = "likes",Modifier.size(20.dp).
                    clickable {  },colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray))
                    Image(painter = painterResource(id = R.drawable.comment_vector), contentDescription = "replys",Modifier.size(13.dp),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray))
                    Image(painter = painterResource(id = R.drawable.bookmarks_vector), contentDescription = "bookmark",Modifier.size(20.dp).clickable {  },
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray))
                }
            }

        }

    }}



@Preview
@Composable
fun prev(){
    LazyColumn {
        items(10) { // Example with 10 items
          //  PostUI()
            HorizontalDivider(color = Color.Gray)
        }
    }


}