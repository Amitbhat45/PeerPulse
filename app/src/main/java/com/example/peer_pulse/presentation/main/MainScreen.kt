package com.example.peer_pulse.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.utilities.Screens

@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
    navController: NavController
){
    Scaffold(
        bottomBar = {
            BottomNavigation(
                selectedButton = BottomNavigationScreens.Main,
                navController = navController
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Text(
                text = authViewModel.college,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}