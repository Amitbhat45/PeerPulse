package com.example.peer_pulse.presentation.community

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
import com.example.peer_pulse.presentation.main.BottomNavigation
import com.example.peer_pulse.presentation.main.BottomNavigationScreens
import com.example.peer_pulse.utilities.ResponseState
import com.example.peer_pulse.utilities.Screens
import kotlinx.coroutines.launch

@Composable
fun CommunityScreen(
    navController: NavController,
    collegeName: String,
    collegeCode: String,
    collegeLogo: Int,
    communityViewModel: CommunityViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
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
                communityViewModel.getGeneralPostsByCollege(collegeName)
                when (val response = communityViewModel.generalPosts.value) {
                    is ResponseState.Error -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = response.message)
                        }
                    }

                    ResponseState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(48.dp))
                        }
                    }

                    is ResponseState.Success -> {
                        if (response.data.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Start posting!!",
                                    modifier = Modifier.padding(8.dp),
                                    fontSize = 32.sp
                                )

                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(8.dp),
                            ) {
                                items(response.data.size) { index ->
                                   Text(text = response.data[index])
                                }
                            }
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
