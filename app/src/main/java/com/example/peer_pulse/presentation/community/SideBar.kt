package com.example.peer_pulse.presentation.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peer_pulse.R
import com.example.peer_pulse.domain.model.colleges
import com.example.peer_pulse.utilities.ResponseState
import com.example.peer_pulse.utilities.ToastMessage


@Composable
fun SideBarSheet(
    collegeCode : String,
    collegeLogo: Int,
    communityViewModel: CommunityViewModel,
) {
    ModalDrawerSheet {
//        Spacer(modifier = Modifier.height(8.dp))
//        NavigationDrawerItem(
//            label = {
//                Text(
//                    text = "Your Communities",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 14.sp
//                )
//            },
//            selected = false ,
//            onClick = {
//
//            },
//            shape = RectangleShape,
//            modifier = Modifier
//                .height(48.dp)
//                .fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Communities",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            },
            selected = false ,
            onClick = {

            },
            shape = RectangleShape,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
        )
        AllCommunities(
            collegeCode = collegeCode,
            collegeLogo = collegeLogo,
            communityViewModel = communityViewModel
        )
    }
}

@Composable
fun AllCommunities(
    collegeCode: String,
    collegeLogo: Int,
    communityViewModel: CommunityViewModel
) {
    LaunchedEffect(key1 = communityViewModel.allCommunityList.value) {
           communityViewModel.getAllCommunities(collegeCode)
    }
    when(val response = communityViewModel.allCommunityList.value){
        is ResponseState.Error ->
            ToastMessage(message = response.message)
        ResponseState.Loading ->
            CircularProgressIndicator()
        is ResponseState.Success -> {
            if(response.data.isEmpty()){
                Text(
                    text = "No Communities",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 14.sp
                )
            } else {
                response.data.forEach {community->
                    CustomNavigationItem(
                        collegeCode = collegeCode,
                        collegeLogo = collegeLogo,
                        communityName = community.name,
                        onCommunityClicked = {

                        }
                    )
                }
            }
        }
    }
}

//@Composable
//fun YourCommunities(
//    collegeCode: String,
//    collegeLogo: Int,
//    communityViewModel: CommunityViewModel,
//){
//    LaunchedEffect(key1 = communityViewModel.yourCommunity.value) {
//        communityViewModel.getCommunitiesOfUser()
//    }
//    when(val response = communityViewModel.yourCommunity.value){
//        is ResponseState.Error ->
//            ToastMessage(message = response.message)
//        ResponseState.Loading ->
//            CircularProgressIndicator()
//        is ResponseState.Success -> {
//            if (response.data.isEmpty()) {
//                Text(
//                    text = "No Communities",
//                    modifier = Modifier.padding(16.dp),
//                    fontSize = 14.sp
//                )
//            } else {
//                response.data.forEach {
//                    var collegeLogoo = communityViewModel.communityList.find { community -> community.name == it }?.logo ?: colleges.find { college -> college.code == collegeCode }?.logo ?: R.drawable.about_vector
//                    if(collegeLogoo == R.drawable.ic_launcher_foreground){
//                        collegeLogoo = collegeLogo
//                    }
//                    CustomNavigationItem(
//                        collegeCode = collegeCode,
//                       collegeLogo = collegeLogoo,
//                        communityName = it,
//                        onCommunityClicked = {
//
//                        }
//                    )
//                }
//            }
//        }
//    }
//}


@Composable
fun CustomNavigationItem(
    collegeCode: String,
    collegeLogo: Int,
    communityName : String,
    onCommunityClicked: () -> Unit
){
    NavigationDrawerItem(
        label = {
            Text(
                text = communityName,
                fontSize = 14.sp
            )
        },
        selected = false ,
        onClick = onCommunityClicked,
        icon = {
            Image(painter = painterResource(id = collegeLogo),
                contentDescription =null,
                modifier = Modifier.size(28.dp)
            )
        },
        shape = RectangleShape,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
    )
}