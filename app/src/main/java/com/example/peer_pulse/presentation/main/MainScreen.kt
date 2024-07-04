package com.example.peer_pulse.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.presentation.postUI.PostViewModel
import com.example.peer_pulse.R
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.presentation.postUI.PostUI
import com.example.peer_pulse.utilities.Screens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    postViewModel: PostViewModel
){
    val userFeedState = postViewModel.userFeedState
    val lazyPagingItems = userFeedState.collectAsLazyPagingItems()

    val pagerState = rememberPagerState(pageCount = { hometabs.size})
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    Scaffold(
        topBar = { TopAppBarWithSearch()},
        bottomBar = {
            BottomNavigation(
                selectedButton = BottomNavigationScreens.Main,
                navController = navController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddPostScreen.route)
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription =null )
            }
        }
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
                // Iterate over the paging items
                for (post in lazyPagingItems.itemSnapshotList.items) {
                    post?.let {
                        PostUI(post = it)
                    }
                }}

                /*lazyPagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { LoadingItem() }
                        }
                        loadState.append is LoadState.Loading -> {
                            item { LoadingItem() }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val e = lazyPagingItems.loadState.refresh as LoadState.Error
                            item { ErrorItem(message = e.error.localizedMessage ?: "An error occurred") }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = lazyPagingItems.loadState.append as LoadState.Error
                            item { ErrorItem(message = e.error.localizedMessage ?: "An error occurred") }
                        }
                    }*/
            /*LazyColumn {
                item (lazyPagingItems){ post->
                    post?.let {
                        PostUI(post = it)
                    }

                }
            }*/
           /* TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth(),
                //containerColor = Color(0xFF1b1a25),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        //color = Color(0xFF8fcce3), // Change the color here
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value])
                    )
                }

            ) {
                hometabs.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        selectedContentColor = Color(0xFFffffff),
                        unselectedContentColor = MaterialTheme.colorScheme.outline,
                        onClick = {
                            pagerState.currentPage
                        },
                        text = { Text(text = hometabs[index].txt1) },

                        )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column (modifier = Modifier.fillMaxSize()){

                    }*/
            /*Text(
                text = authViewModel.college,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )*/

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithSearch() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 25.dp, end = 50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "App Logo", // Ensure contentDescription is descriptive
            Modifier
                .size(35.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(20.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search for keyword",fontSize = 10.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
            ,
            colors = TextFieldDefaults.textFieldColors(
                //backgroundColor = Color.White, // Set a solid background color
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                //textColor = Color.Black // Ensure text color contrasts with the background
            ),
            singleLine = true,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

data class HomeTabs(
    val index: Int,
    var txt1: String
)

val hometabs= listOf(
    HomeTabs(
        0,
        "HOME"
    ),
    HomeTabs(
        1,
        "POPULAR"
    )

)