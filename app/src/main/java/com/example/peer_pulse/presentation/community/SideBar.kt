package com.example.peer_pulse.presentation.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.peer_pulse.R


@Composable
fun SideBarSheet(
    sideBarOpen: Boolean,
    onClose: () -> Unit
) {
    var yourComm by remember {
        mutableStateOf(true)
    }
    var allComm by remember {
        mutableStateOf(false)
    }
    ModalDrawerSheet {
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Your Communities",
                    fontWeight = FontWeight.Bold
                )
            },
            selected = false ,
            onClick = {
                yourComm = !yourComm

            },
            badge = {
                Icon(
                    imageVector = if(yourComm) Icons.Filled.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            },
            shape = RectangleShape
        )
        if(yourComm){
            YourCommunities()
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Available Communities",
                    fontWeight = FontWeight.Bold
                )
            },
            selected = false ,
            onClick = {
                allComm = !allComm

            },
            badge = {
                Text(
                    text = if(!allComm)"See All" else "See Less",
                    fontWeight = FontWeight.Bold
                )
            },
            shape = RectangleShape
        )

    }
}


/*@Preview(showBackground = true)
@Composable
fun SideBarPreview() {
    SideBarSheet()
}*/


@Composable
fun YourCommunities(){
    NavigationDrawerItem(
        label = {
            Text(text = "Create Community")
        },
        selected = false ,
        onClick = {

        },
        icon ={
            Icon(imageVector = Icons.Filled.Add, contentDescription = null )
        },
        shape = RectangleShape
    )
    NavigationDrawerItem(
        label = {
            Text(text = "College Events")
        },
        selected = false ,
        onClick = {

        },
        icon = {
            Image(painter = painterResource(id = R.drawable.drait_logo),
                contentDescription =null,
                modifier = Modifier.size(28.dp)
            )
        },
        shape = RectangleShape
    )
    NavigationDrawerItem(
        label = {
            Text(text = "Student Club")
        },
        selected = false ,
        onClick = {

        },
        icon = {
            Image(painter = painterResource(id = R.drawable.drait_logo),
                contentDescription =null,
                modifier = Modifier.size(28.dp)
            )
        },
        shape = RectangleShape
    )

}