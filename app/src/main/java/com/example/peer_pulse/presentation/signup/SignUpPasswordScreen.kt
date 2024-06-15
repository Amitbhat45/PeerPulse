package com.example.peer_pulse.presentation.signup

import androidx.collection.emptyLongSet
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.utilities.ResponseState
import com.example.peer_pulse.utilities.Screens
import com.example.peer_pulse.utilities.ToastMessage
import kotlinx.coroutines.launch

@Composable
fun SignUpPasswordScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val password = remember { mutableStateOf("") }
    var passwordValid by remember {
        mutableStateOf<Boolean?>(null)
    }
    var secondPasswordValid by remember {
        mutableStateOf<Boolean?>(null)
    }
    val verifiedPassword = remember { mutableStateOf("") }
    var verifyMessage by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            AuthTopBar(
                title = "Sign Up",
                onBackClick = {
                    navController.navigateUp()
                },
                backClick = true
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(6.5f)
            ) {
                Text(
                    text = "You'll need a password.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = password.value,
                    onValueChange = {changedPassword->
                        password.value = changedPassword
                        passwordValid = authViewModel.passwordValidate(changedPassword)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    label = {
                        Text(
                            text = "Password",
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Enter Password"
                        )
                    },
                    isError = passwordValid == false
                )
                Text(
                    text = if(passwordValid == null)
                        "Enter Password( 8~32 characters, include at least one number and one special character)"
                    else if(passwordValid == false)
                        "Invalid Password"
                    else
                        "Valid Password",
                    color = if(passwordValid == null)
                        Color.Gray
                    else if(passwordValid == false)
                        Color.Red
                    else
                        Color.Green,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(4.dp)

                )
                OutlinedTextField(
                    value = verifiedPassword.value,
                    onValueChange = {
                        verifiedPassword.value = it
                        secondPasswordValid = authViewModel.passwordVerify(password.value, it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    label = {
                        Text(
                            text = "Password",
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Re-enter Password"
                        )
                    },
                    isError = secondPasswordValid == false
                )
                Text(
                    text = if(secondPasswordValid == null)
                        "Re-enter Password"
                    else if(secondPasswordValid == false)
                        "Passwords do not match"
                    else
                        "Passwords match",
                    color = if(secondPasswordValid == null)
                        Color.Gray
                    else if(secondPasswordValid == false)
                        Color.Red
                    else
                        Color.Blue,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(3.5f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            authViewModel.password = password.value
                            authViewModel.college = authViewModel.whichCollege(authViewModel.email)
                            verifyMessage = true
                            authViewModel.registerCollege()
                            authViewModel.signUp()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    shape = RoundedCornerShape(4.dp),
                    enabled = passwordValid == true && secondPasswordValid == true
                ) {
                    Text(
                        text = "Join Peer-pulse",
                        fontWeight = FontWeight.Bold
                    )
                    when(val response = authViewModel.registerCollege.value)
                    {
                        is ResponseState.Error ->
                            ToastMessage(message =response.message)
                        ResponseState.Loading -> CircularProgressIndicator()
                        is ResponseState.Success -> {
                            if(response.data == true)
                            {
                                when(val response = authViewModel.signUp.value)
                                {
                                    is ResponseState.Error -> {
                                        ToastMessage(message = response.message)
                                    }
                                    ResponseState.Loading -> {
                                        CircularProgressIndicator()
                                    }
                                    is ResponseState.Success -> {
                                        if(response.data == true)
                                        {
                                            navController.navigate(Screens.PreferenceScreen1.route){
                                                launchSingleTop = true
                                            }
                                        }
                                        else if(response.data == false)
                                        {
                                            ToastMessage(message = "Sign Up Failed")
                                        }
                                        else
                                        {

                                        }
                                    }
                                }
                            }
                            else if(response.data == false)
                            {
                                    ToastMessage(message = "Invalid College Name. Please enter a valid college email id with valid college")
                            }
                            else
                            {

                            }
                        }
                    }
                    if(verifyMessage)
                    ToastMessage(message = "Please check the email verification link sent to your email address")
                }
            }
        }
    }
}