package com.example.peer_pulse.presentation.community

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Gif
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.peer_pulse.MainActivity.Companion.REQUEST_READ_EXTERNAL_STORAGE
import com.example.peer_pulse.MainActivity.Companion.REQUEST_READ_MEDIA_IMAGES
import com.example.peer_pulse.MainActivity.Companion.REQUEST_READ_MEDIA_VISUAL_USER_SELECTED
import com.example.peer_pulse.domain.model.Message
import com.example.peer_pulse.utilities.ResponseState
import com.example.peer_pulse.utilities.ToastMessage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CommunityMessageScreen(
    navController: NavController,
    communityViewModel: CommunityViewModel,
    communityName: String,
    permissionGranted: MutableState<Boolean>,
    collegeCode: String,
    userName : String
) {
    Scaffold(
        topBar = {
            CommunityTopBar(
                title = communityName,
                navController = navController
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (val response = communityViewModel.messagesByCommunity.value) {
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
                                .weight(9f)
                                .padding(8.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Start messaging!!",
                                modifier = Modifier.padding(8.dp),
                                fontSize = 32.sp
                            )

                        }
                        HorizontalDivider()
                        MessageBar(
                            permissionGranted = permissionGranted,
                            communityViewModel = communityViewModel,
                            communityName = communityName,
                            collegeCode = collegeCode,
                            userName = userName
                        )
                    } else {
                        val listState = rememberLazyListState()
                        LaunchedEffect(Unit) {
                            listState.animateScrollToItem(response.data.size - 1)
                        }
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(9f),
                        ) {
                            items(response.data.size) { index ->
                                MessageCard(
                                    message = response.data[index].text,
                                    image = response.data[index].imageUrl,
                                    currentUser = communityViewModel.userId == response.data[index].userId,
                                    time = convertMillisTo24HourFormat(response.data[index].timeStamp),
                                    messageId = response.data[index].messageId,
                                    communityViewModel = communityViewModel,
                                    userName = response.data[index].userName
                                )
                            }
                        }
                        HorizontalDivider()
                        MessageBar(
                            permissionGranted = permissionGranted,
                            communityViewModel = communityViewModel,
                            communityName = communityName,
                            collegeCode = collegeCode,
                            userName = userName
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityTopBar(
    title: String,
    navController: NavController
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigateUp()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun MessageBar(
    permissionGranted: MutableState<Boolean>,
    communityViewModel: CommunityViewModel,
    communityName: String,
    collegeCode: String,
    userName: String
) {
    var messageText by remember {
        mutableStateOf<String>("")
    }
    var image by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uris ->
            image = uris
        }

    val context = LocalContext.current
    val activity = context as ComponentActivity

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkPermissions() {
        when {
            Build.VERSION.SDK_INT <= Build.VERSION_CODES.R -> {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_READ_EXTERNAL_STORAGE
                    )
                } else {
                    permissionGranted.value = true
                    launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                }
            }

            Build.VERSION.SDK_INT == Build.VERSION_CODES.S || Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU -> {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_MEDIA_IMAGES
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                        REQUEST_READ_MEDIA_IMAGES
                    )
                } else {
                    permissionGranted.value = true
                    launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                }
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED),
                        REQUEST_READ_MEDIA_VISUAL_USER_SELECTED
                    )
                } else {
                    permissionGranted.value = true
                    launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .padding(4.dp)
            //.weight(1f)
            .fillMaxWidth()
            .imePadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                checkPermissions()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Image,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
        OutlinedTextField(
            value = messageText,
            onValueChange = {
                messageText = it
            },
            modifier = Modifier
                .weight(6f)
                .padding(4.dp)
                .height(60.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            label = {
                Text(text = "Write a message")
            }
        )
        IconButton(
            onClick = {
                communityViewModel.sendMessage(
                    text = messageText,
                    imageUrl = image.toString(),
                    userId = communityViewModel.userId ?: "",
                    communityName = communityName,
                    collegeCode = collegeCode,
                    timeStamp = System.currentTimeMillis(),
                    userName = userName
                )
                messageText = ""
                image = null
            },
            enabled = messageText != "" || image != null,
            colors = IconButtonDefaults.iconButtonColors(
                disabledContentColor = Color.LightGray,
                contentColor = Color.White,
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
    when(val response = communityViewModel.sendMessage.value){
        is ResponseState.Error -> {
            ToastMessage(message = response.message)
            communityViewModel.resetSendState()
        }
        ResponseState.Loading -> {
            CircularProgressIndicator()
        }
        is ResponseState.Success -> {

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MessageCard(
    messageId: String,
    message: String? = null,
    image: String? = null,
    currentUser: Boolean,
    time: String,
    communityViewModel: CommunityViewModel,
    userName: String
) {
    val scope = rememberCoroutineScope()
    var showDialog by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = if (currentUser) Arrangement.End else Arrangement.Start,
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .widthIn(max = 300.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            scope.launch {
                                showDialog = true
                            }
                        }
                    )
                },
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                if(!currentUser) {
                    Text(
                        text = userName,
                        modifier = Modifier.padding(
                            bottom = 0.dp,
                            top = 8.dp,
                            start = 8.dp,
                            end = 8.dp
                        ),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp
                    )
                }
                if (message.isNullOrBlank()) {
                    Box(

                    ) {
                        GlideImage(
                            model = image ?: "",
                            contentDescription = "image",
                        )
                        Text(
                            text = time,
                            modifier = Modifier.padding(
                                bottom = 0.dp,
                                top = 8.dp,
                                start = 8.dp,
                                end = 8.dp
                            ).align(Alignment.BottomEnd),
                            fontSize = 14.sp,
                            color = Color.LightGray,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = message,
                            modifier = Modifier.padding(
                                top = 0.dp,
                                bottom = 8.dp,
                                end = 8.dp,
                                start = 8.dp,
                            ),
                            fontSize = 13.sp
                        )
                        Text(
                            text = time,
                            modifier = Modifier.padding(
                                bottom = 0.dp,
                                top = 8.dp,
                                start = 8.dp,
                                end = 8.dp
                            ),
                            fontSize = 11.sp,
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
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
                        text = "Are you sure you want to delete the message?"
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text(text = "No")
                        }
                        TextButton(
                            onClick = {
                                communityViewModel.deleteMessage(messageId)
                                showDialog = false
                            }
                        ) {
                            Text(text = "Delete")
                        }
                    }
                }
            }
        }
    }
    when(val response = communityViewModel.deleteMessage.value){
        is ResponseState.Error -> {
            ToastMessage(message = response.message)
        }
        ResponseState.Loading -> {
            CircularProgressIndicator()
        }
        is ResponseState.Success -> {
            if(response.data){
                ToastMessage(message = "Deletion done successfully")
                communityViewModel.resetDeleteState()
            }
        }
    }
}


fun convertMillisTo24HourFormat(timeInMillis: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    val resultDate = Date(timeInMillis)
    return sdf.format(resultDate)
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewCommunityMessageScreen() {
//    MessageCard(
//        currentUser = true,
//        time = "12:01",
//        message = "sfsjsdiofgfgoifgjsdgjoifbofbjofbjofbjoifbiofjboifjbijfbbjs",
//        image = null,
//        messageId = "s"
//    )
//}