package com.example.peer_pulse.presentation.postUI

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.R

@Composable
fun PostUI(
    //post : Post
){
    Card(onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(0.dp)
    ) {

            Column (
                Modifier
                    .fillMaxSize()
                    .padding(15.dp)){
                Row (Modifier.fillMaxWidth()){
                    Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription ="topicimage" ,
                        Modifier
                            .size(35.dp)
                            .clip(CircleShape))
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Topic_name" ,fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "2h",fontSize = 10.sp)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "Clgname",
                            fontSize = 10.sp,
                            //fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(Modifier.fillMaxWidth()) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "This is the title of the Post",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "this is the description of the post it should be a maximum of only two lines, it shouldn't exceed that abcd efgh ijkl mnopqrstuv",
                            fontSize = 14.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your post image resource
                        contentDescription = "postimage",
                        Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .align(Alignment.Top)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                    horizontalArrangement =  Arrangement.spacedBy(130.dp, alignment = Alignment.CenterHorizontally)) {
                    Image(painter = painterResource(id = R.drawable.likes_vector), contentDescription = "likes",Modifier.size(20.dp).
                    clickable {  })
                    Image(painter = painterResource(id = R.drawable.comment_vector), contentDescription = "replys",Modifier.size(16.dp))
                    Image(painter = painterResource(id = R.drawable.bookmarks_vector), contentDescription = "bookmark",Modifier.size(20.dp).clickable {  },
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black))
                }
            }

        }

    }



@Preview
@Composable
fun prev(){
    LazyColumn {
        items(10) { // Example with 10 items
            PostUI()
            HorizontalDivider(color = Color.Gray)
        }
    }


}