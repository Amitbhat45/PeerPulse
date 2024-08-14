package com.example.peer_pulse.presentation.replies

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peer_pulse.R
import com.example.peer_pulse.presentation.postUI.PostViewModel
import com.example.peer_pulse.utilities.ResponseState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReplyComponent(
    postViewModel: PostViewModel,
    postId : String
) {
    LaunchedEffect(key1 = postViewModel.replyIdsByPost.value) {
        postViewModel.getRepliesId(postId)
    }
    when (val response = postViewModel.replyIdsByPost.value) {
        is ResponseState.Error -> {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = response.message,
                    fontSize = 20.sp
                )
            }
        }

        ResponseState.Loading -> {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.size(100.dp))
            }
        }

        is ResponseState.Success -> {
            if (response.data.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No replies found",
                        fontSize = 20.sp
                    )
                }
            } else {
                val replyList = response.data
                replyList.forEach {
                    ReplyItem(
                        collegeLogo = it.collegeLogo,
                        displayName = it.college,
                        time = it.timeStamp,
                        content = it.content,
                        like = it.likes
                    )
                }
            }
        }
    }
}

@Composable
fun ReplyItem(
    collegeLogo : Int,
    displayName : String,
    time : Long,
    content : String,
    like : Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Image(
            painter = painterResource(id = if(collegeLogo == 0 ) R.drawable.ic_launcher_foreground else collegeLogo),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = displayName,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = convertMillisTo24HourFormat(time),
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = content,
                color = Color.White,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {

                    },
                    modifier = Modifier.width(56.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.likes_vector),
                            contentDescription = "Like button",
                            colorFilter = ColorFilter.tint(Color.Gray),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if(like == 0)"Like" else like.toString(),
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))
                IconButton(
                    onClick = {

                    },
                    modifier = Modifier.width(64.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.comment_vector),
                            contentDescription = "Like button",
                            colorFilter = ColorFilter.tint(Color.Gray),
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "Reply",
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

fun convertMillisTo24HourFormat(timeInMillis: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    val resultDate = Date(timeInMillis)
    return sdf.format(resultDate)
}

@Preview
@Composable
fun PreviewReplyComponent() {
    ReplyItem(
        collegeLogo = R.drawable.ic_launcher_foreground,
        displayName = "College Name",
        time = System.currentTimeMillis(),
        content = "This is a reply",
        like = 0
    )
}