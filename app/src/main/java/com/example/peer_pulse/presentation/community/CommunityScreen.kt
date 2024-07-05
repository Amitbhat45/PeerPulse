package com.example.peer_pulse.presentation.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.peer_pulse.presentation.main.BottomNavigation
import com.example.peer_pulse.presentation.main.BottomNavigationScreens

@Composable
fun communityScreen(navController: NavController){
    var sideBarOpen by remember { mutableStateOf(false) }
    Scaffold(
topBar = {
    CustomTopAppBar(
        title = "General Posts",
        onMenuClicked = {
            if(sideBarOpen==true) sideBarOpen=false
            else sideBarOpen=true
        }
    )
},
        bottomBar = {
            BottomNavigation(
                selectedButton = BottomNavigationScreens.Community,
                navController = navController
            )
        }
    ) {
        Column(

            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (sideBarOpen) {
                // Show the side bar if sideBarOpen is true
                SideBarSheet(
                    sideBarOpen = sideBarOpen,
                    onClose = { sideBarOpen = false }
                )
            }
            Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()){
                Text("No Posts Yet")
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
                        fontSize = 20.sp
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
