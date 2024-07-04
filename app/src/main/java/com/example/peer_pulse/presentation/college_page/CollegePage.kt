package com.example.peer_pulse.presentation.college_page

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.peer_pulse.presentation.preferences.PageHeader
import com.example.peer_pulse.presentation.preferences.PostLazyColumn

@Composable
fun CollegePage(
    //navController: NavController,
    collegeName : String
){
    Scaffold(

    ) {
        var selectedScreen by remember {
            mutableStateOf("Overview")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PageHeader(
                //navController = navController,
                preferenceId = collegeName,
                isPreference = false
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { 
                        selectedScreen = "Overview"
                    },
                    modifier = Modifier.weight(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = if(selectedScreen == "Overview") Color.Black else Color.Gray,
                        containerColor = Color.Transparent
                    ),
                    shape = RectangleShape
                ) {
                    Column {
                        Text(
                            text = "Overview",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp),
                            fontWeight = FontWeight.Black
                        )
                        HorizontalDivider(
                            thickness = 4.dp,
                            color = if(selectedScreen == "Overview") Color.Gray else Color.Transparent
                        )
                    }
                }
                TextButton(
                    onClick = {
                        selectedScreen = "Ratings"
                    },
                    modifier = Modifier.weight(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = if(selectedScreen == "Ratings") Color.Black else Color.Gray,
                        containerColor = Color.Transparent
                    ),
                    shape = RectangleShape
                ) {
                    Column {
                        Text(
                            text = "Ratings",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        HorizontalDivider(
                            thickness = 4.dp,
                            color = if(selectedScreen == "Ratings") Color.Gray else Color.Transparent
                        )
                    }

                }
            }
            if(selectedScreen == "Overview"){

            }
            else if (selectedScreen == "Ratings"){

            }
        }
    }
}

@Preview
@Composable
fun PreviewCollege(){
    CollegePage(collegeName = "cursor" )
}