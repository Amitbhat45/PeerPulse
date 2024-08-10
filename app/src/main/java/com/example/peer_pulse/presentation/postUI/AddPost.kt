package com.example.peer_pulse.presentation.postUI

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Gif
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.peer_pulse.R
import com.example.peer_pulse.domain.model.preferences
import com.example.peer_pulse.utilities.rememberImeState
import com.example.peer_pulse.MainActivity.Companion.REQUEST_READ_EXTERNAL_STORAGE
import com.example.peer_pulse.MainActivity.Companion.REQUEST_READ_MEDIA_IMAGES
import com.example.peer_pulse.MainActivity.Companion.REQUEST_READ_MEDIA_VISUAL_USER_SELECTED
import com.example.peer_pulse.domain.model.trialPreferences
import com.example.peer_pulse.utilities.ResponseState
import com.example.peer_pulse.utilities.Screens
import com.example.peer_pulse.utilities.ToastMessage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPost(
    navController: NavController,
    postViewModel: PostViewModel,
    permissionGranted: MutableState<Boolean>,
    collegeLogo : Int,
    collegeName : String
) {
    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var images by remember {
        mutableStateOf<List<Uri?>>(listOf())
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    var preferencesText by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val activity = context as ComponentActivity
    var collegeChosen by remember {
        mutableStateOf("")
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia()) {uris->
            images = uris
        }
    var selectedForCollege by remember {
        mutableStateOf(false)
    }
    var forHeader by remember {
        mutableStateOf("Choose")
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkPermissions() {
        when {
            Build.VERSION.SDK_INT <= Build.VERSION_CODES.R -> {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
                } else {
                    permissionGranted.value = true
                    launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                }
            }
            Build.VERSION.SDK_INT == Build.VERSION_CODES.S || Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU -> {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), REQUEST_READ_MEDIA_IMAGES)
                } else {
                    permissionGranted.value = true
                    launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                }
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED), REQUEST_READ_MEDIA_VISUAL_USER_SELECTED)
                } else {
                    permissionGranted.value = true
                    launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                }
            }
        }
    }
    /*LaunchedEffect(permissionGranted) {
        if (permissionGranted.value) {
            launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            permissionGranted.value = false
        }
    }*/
    Scaffold(
        topBar = {
            PostTitleBar(
                navController = navController,
                postViewModel = postViewModel,
                titleText = titleText,
                descriptionText = descriptionText,
                images = images,
                preferencesText = preferencesText,
                collegeName = collegeChosen
            )
        }
    ) {
        val imeState = rememberImeState()
        val scrollState = rememberScrollState()

        LaunchedEffect(key1 = imeState.value) {
            if(imeState.value){
                scrollState.scrollTo(scrollState.maxValue)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(it)
                .padding(8.dp)
                .verticalScroll(scrollState)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
//                    .weight(0.5f)
            ) {
                Image(
                    painter = painterResource(id = collegeLogo ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = {
                        showDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(
                        forHeader,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                OutlinedTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    placeholder = {
                        Text(
                        "Title",
                        color = Color.Gray,
                            style = MaterialTheme.typography.headlineMedium
                    ) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                       focusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White
                    ),
                    textStyle = MaterialTheme.typography.headlineMedium,
                )
                OutlinedTextField(
                    value = descriptionText,
                    onValueChange = { descriptionText = it },
                    placeholder = { Text(
                        "Body",
                        color = Color.Gray,
                        style = MaterialTheme.typography.titleMedium
                    ) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                       unfocusedIndicatorColor = Color.Transparent,
                      focusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White
                    ) ,
                    textStyle = MaterialTheme.typography.titleMedium,
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .weight(4f)
                        .padding(4.dp)
                ) {
                    items(images.size) { index ->
                        AsyncImage(
                            model = if (images[index] != null) images[index] else "",
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(4.dp)
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .background(Color.Gray)
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                thickness = 1.dp
            )
            Row (
            //    modifier = Modifier.weight(0.5f)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                ) {
                    IconButton(
                        onClick = {
                            checkPermissions()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Image,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Gif,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Poll,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
            if (showDialog) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showDialog = false
                    },
                    sheetState = sheetState,
                    scrimColor = Color.DarkGray,
                    containerColor = Color.Black,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "Choose who can see your post",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        HorizontalDivider(color = Color.LightGray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Available Pages",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                        PagesButton(
                            onClick = {
                                preferencesText = it
                                showDialog = false
                                selectedForCollege = false
                                forHeader = it
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = Color.LightGray)
                        Text(text = "My college",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            onClick = {
                                collegeChosen = collegeName
                                selectedForCollege = true
                                preferencesText = ""
                                showDialog = false
                                forHeader = "College"
                                Log.d("College", collegeChosen)
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = if(!selectedForCollege)Color.Transparent  else Color.Blue,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = collegeLogo),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(text = collegeName, color = Color.White, fontSize = 14.sp)
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
fun PostTitleBar(
    navController: NavController,
    postViewModel: PostViewModel,
    titleText : String,
    descriptionText : String,
    images : List<Uri?>,
    preferencesText : String,
    collegeName: String,
) {
    val showButton = titleText.isNotEmpty() && descriptionText.isNotEmpty() && (preferencesText.isNotEmpty() || collegeName.isNotEmpty() )
    TopAppBar(
        title = {

        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier.padding(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null
                )
            }
        },
        actions = {
            Button(
                onClick = {
                   postViewModel.savePost(
                        preferences = preferencesText,
                       preferencesId = preferencesText,
                       title = titleText,
                       description = descriptionText,
                       images = images,
                       collegeName = collegeName
                   )

                },
                colors = ButtonDefaults.buttonColors(
                   containerColor = Color.Transparent,
                    contentColor = if(showButton) Color.White else Color.Gray
                ),
                enabled = showButton
            ) {
                when(val response = postViewModel.savePostState.value){
                    is ResponseState.Error -> {
                        Text(
                            "Post",
                            fontWeight = FontWeight.Bold
                        )
                        ToastMessage(message = response.message)
                    }
                    ResponseState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    is ResponseState.Success -> {
                        if(response.data != null){
                            navController.navigate(Screens.MainScreen.route)
                            postViewModel.resetState()
                        }
                        else
                        {
                            Text(
                                "Post",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },
       colors = TopAppBarColors(
           containerColor = Color.Black,
           navigationIconContentColor = Color.White,
           actionIconContentColor = Color.White,
           titleContentColor = Color.White,
           scrolledContainerColor = Color.Black
       )
    )
}


@Composable
fun PagesButton(
    onClick: (String) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var selectedPreference by remember { mutableStateOf<String?>(null) }
        trialPreferences.forEach {
            val selected = it.id == selectedPreference
            Card(
                onClick = {
                    selectedPreference = if (selected) null else it.id
                    onClick(it.id)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if(!selected)Color.Transparent  else Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = it.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .padding(4.dp)
                    )
                    Text(
                        text = it.id,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomIcons() {
//    val imeVisible by rememberUpdatedState(WindowInsets.isImeVisible)
//    val imeHeight by animateDpAsState(
//        targetValue = WindowInsets.ime.getBottom(LocalDensity.current).dp,
//        label = "IME height"
//    )

    }


//@Preview
//@Composable
//fun AddPostPreview() {
//    AddPost()
//}
