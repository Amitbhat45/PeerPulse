package com.example.peer_pulse.presentation.postUI

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
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

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState

import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.peer_pulse.R
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.domain.model.getPreferenceById
import com.example.peer_pulse.presentation.replies.ReplyComponent
import com.example.peer_pulse.presentation.signup.AuthTopBar
import com.example.peer_pulse.utilities.Screens

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

import com.example.peer_pulse.utilities.rememberImeState


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable

fun PostInsideView(
    post: post,
    navController: NavController,
    postViewModel: PostViewModel,
    collegeLogo : Int,
    collegeName : String
){
    val post_id=post.id

    var images by remember { mutableStateOf<List<String>?>(null) }
    var error by remember { mutableStateOf<Exception?>(null) }

    LaunchedEffect(post_id) {
        fetchImagesByPostId(post_id,
            onSuccess = { fetchedImages -> images = fetchedImages },
            onFailure = { exception -> error = exception }
        )
    }
    var Reply by remember {
        mutableStateOf("")
    }
    val pref = getPreferenceById(post.preferences)
    Scaffold(
        topBar = {
            AuthTopBar(
                title = "Peer Pulse",
                onBackClick = {
                    navController.navigateUp()
                },
                backClick = true
            )
        }
    ) {
        Column (
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){
            Column(
                Modifier
                    .weight(9f)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                        post.collegeCode?.let { it1 ->
                            getCollegelogo(
                                it1
                            )
                        }?.let { it2 -> painterResource(it2) }?.let { it3 ->
                            Image(
                                painter = it3,
                                contentDescription = "topicimage",
                                Modifier
                                    .size(35.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if(post.preferences==""){
                                Text(text = "${post.collegeCode?.let { it1 ->
                                    findCollegeshortnameBYname(it1)
                                }}", fontSize = 15.sp,
                                    fontWeight = Bold,
                                    /*modifier = Modifier.clickable {
                                        navController.navigate(Screens.PagesScreen.createRoute(post.preferences)) {
                                            launchSingleTop = true
                                        }
                                    }*/
                                )
                            }
                            else{
                                Text(text = "${post.preferences}", fontSize = 15.sp,
                                    fontWeight = Bold,
                                    modifier = Modifier.clickable {
                                        navController.navigate(Screens.PagesScreen.createRoute(post.preferences)) {
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = getTimeAgo(post.timestamp),
                                fontSize = 10.sp,
                                color = Color.Gray
                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Column(Modifier.padding(start = 20.dp, end = 20.dp)) {
                    Text(
                        text = post.title,
                        fontSize = 25.sp,
                        fontWeight = Bold,

                        )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = post.description,
                        fontSize = 16.sp,

                        )
                }

                Log.d("ammmmmmit", "${post.images[0]}")
                Spacer(modifier = Modifier.height(5.dp))
                /*  GlideImage(model = images?.get(0), contentDescription ="" ,
                      modifier = Modifier
                          .height(400.dp)
                          .fillMaxWidth()
                          .clip(RoundedCornerShape(0.dp)))*/
                ImageSlider(images = images)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceAround
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
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
                            colorFilter = ColorFilter.tint(Color.Gray)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
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
                            colorFilter = ColorFilter.tint(Color.Gray)
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
                            colorFilter = ColorFilter.tint(Color.Gray)
                        )
                    }

                }
                Spacer(modifier = Modifier.height(30.dp))
                ReplyComponent(
                    postViewModel = postViewModel,
                    postId = post.id,
                )
            }
            Column(
                modifier = Modifier
                    //  .weight(1f)
                    .imePadding()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
            ) {
                OutlinedTextField(
                    value = Reply,
                    onValueChange = { Reply = it },
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
                                    post.id,
                                    Reply,
                                    collegeName,
                                    collegeLogo
                                )
                                Reply = ""
                            },
                            enabled = Reply.isNotEmpty()
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

fun fetchImagesByPostId(postId: String, onSuccess: (List<String>?) -> Unit, onFailure: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val postRef = db.collection("posts").document(postId)

    postRef.get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val images = document.get("images") as? List<String>
                onSuccess(images)
            } else {
                onFailure(Exception("Post not found"))
            }
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}
@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ImageSlider(images: List<String>?) {

    val imageList = images ?: emptyList()
    val scope = rememberCoroutineScope()

    // Show nothing if there are no images
    if (imageList.isEmpty()) {
        Spacer(modifier = Modifier.height(0.dp))
        return
    }
    val pagerState = rememberPagerState(pageCount = {imageList.size})
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            // count = imageList.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            GlideImage(
                model = imageList[page],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clip(RoundedCornerShape(0.dp))
            )
        }

        // Show previous arrow
        if (pagerState.currentPage > 0) {
            IconButton(
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }

                },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(8.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Previous", tint = Color.White)
            }
        }

        // Show next arrow
        if (pagerState.currentPage < imageList.size - 1) {
            IconButton(
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }

                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(8.dp)
            ) {
                Icon(Icons.Filled.ArrowForward, contentDescription = "Next", tint = Color.White)
            }
        }
    }
}