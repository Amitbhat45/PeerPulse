package com.example.peer_pulse.presentation.community

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.peer_pulse.presentation.main.BottomNavigation
import com.example.peer_pulse.presentation.main.BottomNavigationScreens
import com.example.peer_pulse.presentation.postUI.PostUI
import com.example.peer_pulse.presentation.postUI.PostViewModel
import com.example.peer_pulse.utilities.ResponseState
import com.example.peer_pulse.utilities.Screens
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommunityScreen(
    navController: NavController,
    collegeName: String,
    collegeCode: String,
    collegeLogo: Int,
    communityViewModel: CommunityViewModel,
    postViewModel: PostViewModel,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val clgposts = communityViewModel.clgPosts
    val lazyPagingItems = clgposts.collectAsLazyPagingItems()

    ModalNavigationDrawer(
        drawerContent = {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
            ) {
                SideBarSheet(
                    collegeCode = collegeCode,
                    communityViewModel = communityViewModel,
                    collegeLogo = collegeLogo,
                    navController = navController
                )
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    title = collegeName,
                    onMenuClicked = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigation(
                    selectedButton = BottomNavigationScreens.Community,
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
                Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                for (post in lazyPagingItems.itemSnapshotList.items) {
                                    post?.let {
                                        Log.d("Posts", "Post: $it")
                                       PostUI(post = it, navController,postViewModel)
                                        HorizontalDivider(
                                            Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }

            }
        }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    onMenuClicked: () -> Unit
) {
    Column {
        TopAppBar(

            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { onMenuClicked() },
                        modifier = Modifier.padding(start = 0.dp)
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 16.sp
                    )
                }
            },

            )
        Spacer(modifier = Modifier.height(2.dp))
        HorizontalDivider(
            Modifier.fillMaxWidth()
        )
    }

}
