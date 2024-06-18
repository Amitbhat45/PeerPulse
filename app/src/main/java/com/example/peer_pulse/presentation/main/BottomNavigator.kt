package com.example.peer_pulse.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.peer_pulse.utilities.Screens

enum class BottomNavigationScreens( val route : String,val icon :ImageVector ) {
    Main(Screens.MainScreen.route,Icons.Outlined.Home),
    Profile(Screens.ProfileScreen.route, Icons.Outlined.AccountCircle),
}

@Composable
fun BottomNavigation(
    selectedButton : BottomNavigationScreens,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (item in BottomNavigationScreens.entries) {
            Column {
                Image(
                    imageVector = item.icon,
                    contentDescription = item.name,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(item.route)
                        }
                        .padding(8.dp)
                        .padding(top = 8.dp)
                        .size(24.dp)
                        .align(Alignment.CenterHorizontally),
                    colorFilter = if (selectedButton == item) {
                        ColorFilter.tint(Color.White)
                    } else {
                        ColorFilter.tint(Color.Gray)
                    }
                )
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (selectedButton == item) {
                        Color.White
                    } else {
                        Color.Gray
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

