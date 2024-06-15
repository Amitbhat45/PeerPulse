package com.example.peer_pulse.presentation.signup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.utilities.Screens


@Composable
fun SignUpEmailScreen(
   navController: NavController,
    authViewModel: AuthViewModel
) {
    val email = remember { mutableStateOf("") }
    var valid by remember {
        mutableStateOf<Boolean?>(null)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
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
        Column (
            modifier = Modifier.padding(it)
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(6.5f)
            ) {
                Text(
                    text = "Verify to join your community",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        text = "Enter your college email",
                        fontSize = 20.sp,
                    )
                    IconButton(
                        onClick = {
                            expanded = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Info Button",
                            modifier = Modifier.size(20.dp)
                        )
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
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .padding(16.dp)
                                .width(250.dp)
                        ) {
                            Column {
                                Text("Important: \nWelcome! Our app is exclusively available to students affiliated with VTU. Please use your university-provided email address to register and access all features.")
                            }
                        }
                    }
                }
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { changedEmail ->
                        email.value = changedEmail
                        valid = authViewModel.emailValidator(changedEmail)
                    },
                    label = {
                        Text(
                            text = "College Email"
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Enter your college email"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = valid == false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Text(
                    text = if (valid == true) "The email is valid!!" else if (valid == false) "Please enter a valid college email address" else "",
                    color = if (valid == true) Color.Blue else Color.Red,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(3.5f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "By creating an account , you agree to Peer-Pulse's Terms of Service and Privacy Policy",
                    fontSize = 10.sp,
                )
                Button(
                    onClick = {
                        authViewModel.email = email.value
                        Log.d("SignUpEmailScreen", "Email: ${authViewModel.email}")
                        navController.navigate(Screens.SignUpPasswordScreen.route){
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    shape = RoundedCornerShape(4.dp),
                    enabled = valid == true
                ) {
                    Text(
                        text = "Continue",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTopBar(
    title : String,
    onBackClick : (() -> Unit)?=null,
    backClick : Boolean =false
){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
            )
        },
        navigationIcon = {
           if(backClick)
           {
               if (onBackClick != null) {
                   IconButton(
                       onClick = onBackClick
                   ) {
                       Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = "Back button" )
                   }
               }
           }
            else{

           }
        }
    )
}