package com.example.peer_pulse.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.peer_pulse.R
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

            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            var isEditing by remember { mutableStateOf(false) }
            var username by remember {
                mutableStateOf(profileViewModel.userName)
            }
            val focusRequester = remember { FocusRequester() }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 25.dp, bottom = 16.dp, end = 16.dp)
                    .weight(9f)
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    if (isEditing) {
                        BasicTextField(
                            value = username,
                            onValueChange = {
                                username = it
                                profileViewModel.updateUsername(username)
                            },
                            textStyle = TextStyle(
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier
                                .focusRequester(focusRequester)
                        )
                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }
                    } else {
                        Text(
                            text = username,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,

                            )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = if (isEditing) "SAVE" else "EDIT",
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable { isEditing = !isEditing },
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = authViewModel.email,
                    fontSize = 10.sp,
                )

                Spacer(modifier = Modifier.height(10.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.5.dp)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    card(text = "My Posts", imageVector = R.drawable.posts) {
                        navController.navigate(Screens.MyPostScreen.route)
                        profileViewModel.myPosts()
                    }
                    card(text = "Bookmarks", imageVector = R.drawable.bookmarks_vector) {
                        navController.navigate(Screens.BookmarkedPostScreen.route)
                        profileViewModel.getBookmarkedPosts()
                    }
                    card(text = "Following", imageVector = R.drawable.following_vector) {
                        navController.navigate(Screens.FollowingPageScreen.route)
                        profileViewModel.getFollowingPages()
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                more(text = "About", imageVector = R.drawable.about_vector, {})
                Spacer(modifier = Modifier.height(15.dp))
                HorizontalDivider(
                    Modifier.fillMaxWidth()
                )
                more(text = "Terms and Condition", imageVector = R.drawable.tandc_vector, {})
                Spacer(modifier = Modifier.height(15.dp))
                HorizontalDivider(
                    Modifier.fillMaxWidth()
                )
                LogOutButton(
                    authViewModel = authViewModel,
                    onSuccess = {
                        authViewModel.resetState()
                        navController.navigate(Screens.AuthGraph.route) {
                            popUpTo(Screens.AuthGraph.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    onDialogLogOutButtonClicked = {
                        authViewModel.signOut()
                    }
                )
            }

        }
    }
}

@Composable
fun LogOutButton(
    authViewModel: AuthViewModel,
    onSuccess: () -> Unit,
    onDialogLogOutButtonClicked: () -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 15.dp), horizontalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(R.drawable.logout_vector), contentDescription = "image")
        Spacer(modifier = Modifier.width(30.dp))
        Text(
            text = "Log Out",
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
            contentDescription = "forward",
            Modifier.clickable { expanded = true })

    }
    //Text(text = "Log Out")
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
                    ) {
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

@Composable
fun card(text: String, imageVector: Int, onClick: () -> Unit) {
    OutlinedCard(
        onClick = { onClick() },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(100.dp)
            .height(85.dp)
    ) {
        Column( // Use Box for flexible positioning
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = imageVector), contentDescription = "")
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = text,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 14.sp)
            )
        }

    }
}

@Composable
fun more(text: String, imageVector: Int, onClick: () -> Unit) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 15.dp), horizontalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = imageVector), contentDescription = "image")
        Spacer(modifier = Modifier.width(30.dp))
        Text(
            text = text,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
            contentDescription = "forward",
            Modifier.clickable { onClick() })

    }

}

