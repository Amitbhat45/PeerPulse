package com.example.peer_pulse.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.presentation.main.BottomNavigation
import com.example.peer_pulse.presentation.main.BottomNavigationScreens
import com.example.peer_pulse.utilities.ResponseState
import com.example.peer_pulse.utilities.Screens
import com.example.peer_pulse.utilities.ToastMessage

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(
                selectedButton = BottomNavigationScreens.Profile,
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
            LogOutButton(
                authViewModel = authViewModel,
                onSuccess = {
                    authViewModel.resetState()
                    navController.navigate(Screens.LandingScreen.route) {
                        popUpTo(Screens.LandingScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onDialogLogOutButtonClicked = {
                    authViewModel.signOut()
                }
            )
        }
    }
}

@Composable
fun LogOutButton(
    authViewModel: AuthViewModel,
    onSuccess : () -> Unit,
    onDialogLogOutButtonClicked : () -> Unit
){
    var expanded by remember {
        mutableStateOf(false)
    }
    TextButton(
        onClick = {
            expanded = true
        }
    ) {
        Text(text = "Log Out")
        when (val response = authViewModel.signOut.value) {
            is ResponseState.Error -> {
                ToastMessage(message = response.message)
            }

            ResponseState.Loading -> {
                CircularProgressIndicator()
            }

            is ResponseState.Success -> {
                if (response.data == true) {
                    onSuccess()
                } else if (response.data == false) {
                    ToastMessage(message = "Error Logging Out")
                } else {

                }
            }
        }
    }
    if (expanded) {
        Dialog(
            onDismissRequest = {
                expanded = false
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Are you sure you want to log out?\nWe cannot retrieve forgotten passwords!"
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        TextButton(
                            onClick = {
                                expanded = false
                            }
                        ) {
                            Text(text = "No")
                        }
                        TextButton(
                            onClick = {
                                onDialogLogOutButtonClicked()
                                expanded = false
                            }
                        ) {
                            Text(text = "Log-out")
                        }
                    }
                }
            }
        }
    }
}