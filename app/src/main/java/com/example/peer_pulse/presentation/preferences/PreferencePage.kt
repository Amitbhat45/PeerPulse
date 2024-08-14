package com.example.peer_pulse.presentation.preferences

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.peer_pulse.R
import com.example.peer_pulse.domain.model.trialPreferences
import com.example.peer_pulse.presentation.postUI.PostCard

import com.example.peer_pulse.presentation.postUI.PostUI
import com.example.peer_pulse.presentation.postUI.PostViewModel
import com.example.peer_pulse.utilities.ResponseState

@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun PreferencePage(
    preferenceId : String,
    preferencesViewModel: PreferencesViewModel,
    postViewModel: PostViewModel,
    navController: NavController,

) {
    val userFeedState = preferencesViewModel.TopicpageFeed
    val lazyPagingItems = userFeedState.collectAsLazyPagingItems()
    Scaffold(

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PageHeader(
                //navController = navController,
                preferenceId = preferenceId,
            )
            //Spacer(modifier = Modifier.height(3.dp))
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
                followbuttonrow(preferenceId = preferenceId)
                for (post in lazyPagingItems.itemSnapshotList.items) {
                    post?.let {
                        PostUI(post = it,navController,postViewModel)
                        HorizontalDivider(
                            Modifier.fillMaxWidth()
                        )
                    }
                }}
        }
    }
}


@Composable
fun PageHeader(
    //navController: NavController,
    preferenceId : String,
){
    val logo = trialPreferences.find { it.id == preferenceId}?.logo ?: R.drawable.following_vector
    val background = trialPreferences.find { it.id == preferenceId}?.background ?: R.drawable.ic_launcher_background
    Box(modifier = Modifier.height(200.dp)) {
        Image(
            painter = painterResource(id = background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .align(Alignment.TopStart),
        ){
            IconButton(
                onClick = {
                    // navController.navigateUp()
                }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription =null )
            }
        }
        Row(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Image(
                painter = painterResource(id = logo),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )
        }
    }
   
}

@Composable
fun followbuttonrow(preferenceId : String,){
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = preferenceId,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Public"
            )
        }
        Button(
            onClick = {

            },
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(text = "Follow")
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreferencePagePreview() {
    PageHeader(preferenceId = "College Events")
    followbuttonrow(preferenceId ="College Events" )
}

