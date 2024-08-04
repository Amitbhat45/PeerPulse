package com.example.peer_pulse.presentation.preferences

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.peer_pulse.presentation.AuthViewModel
import com.example.peer_pulse.presentation.signup.AuthTopBar
import com.example.peer_pulse.utilities.Screens

@Composable
fun Preferences1 (navController: NavController,
                  authViewModel: AuthViewModel
){
    var selectedPrefs = remember { mutableStateListOf<String>() }
    Scaffold(
        topBar = {
            AuthTopBar(
                title = "Set Your Preferences",
                )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .padding(14.dp)
                    .weight(9f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "You'll see related content on your feed",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(4.dp)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Academic Interest",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(4.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PreferenceCard(text = "Software Engineering", selected = false) {
                            updateSelectedPrefs("Software Engineering",!selectedPrefs.contains("Software Engineering"), selectedPrefs)
                        }
                        PreferenceCard(text = "Electrical Engineering", selected = false) {
                            updateSelectedPrefs("Electrical Engineering",!selectedPrefs.contains("Electrical Engineering"), selectedPrefs)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PreferenceCard(text = "Civil Engineering", selected = false) {
                            updateSelectedPrefs("Civil Engineering",!selectedPrefs.contains("Civil Engineering"), selectedPrefs)
                        }
                        PreferenceCard(text = "Mechanical Engineering", selected = false) {
                            updateSelectedPrefs("Mechanical Engineering",!selectedPrefs.contains("Mechanical Engineering"), selectedPrefs)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PreferenceCard(text = "Architectural Engineering", selected = false) {
                            updateSelectedPrefs("Architectural Engineering",!selectedPrefs.contains("Architectural Engineering"), selectedPrefs)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Extracurricular Activities ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(4.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PreferenceCard(text = "Sports", selected = false) {
                            updateSelectedPrefs("Sports",!selectedPrefs.contains("Sports"), selectedPrefs)

                        }
                        PreferenceCard(text = "Music", selected = false) {
                            updateSelectedPrefs("Music",!selectedPrefs.contains("Music"), selectedPrefs)

                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PreferenceCard(text = "Gaming", selected = false) {
                            updateSelectedPrefs("Gaming",!selectedPrefs.contains("Gaming"), selectedPrefs)

                        }
                        PreferenceCard(text = "College Events", selected = false) {
                            updateSelectedPrefs("College Events",!selectedPrefs.contains("College Events"), selectedPrefs)

                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Other Interests",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(4.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PreferenceCard(text = "Career Development", selected = false) {
                            updateSelectedPrefs("Career Development",!selectedPrefs.contains("Career Development"), selectedPrefs)
                        }
                        PreferenceCard(text = "Tech and Innovation", selected = false) {
                            updateSelectedPrefs("Tech and Innovation",!selectedPrefs.contains("Tech and Innovation"), selectedPrefs)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PreferenceCard(text = "Complaints and Feedback", selected = false) {
                            updateSelectedPrefs("Complaints and Feedback",!selectedPrefs.contains("Complaints and Feedback"), selectedPrefs)
                        }
                        PreferenceCard(text = "Love and Relationship", selected = false) {
                            updateSelectedPrefs("Love and Relationship",!selectedPrefs.contains("Love and Relationship"), selectedPrefs)
                        }
                    }
                }

            }
            Column(
                modifier = Modifier
                    .padding(14.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        authViewModel.userPreferences(selectedPrefs)
                        navController.navigate(Screens.MainGraph.route) {
                            popUpTo(Screens.AuthGraph.route) {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(
                        text = "Done",
                        fontWeight = FontWeight.Bold
                    )

                }
            }
}
    }
}

@Composable
fun PreferenceCard(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    val isSelected = remember { mutableStateOf(selected) }
    OutlinedCard(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .clickable {
                isSelected.value =!isSelected.value;
                onSelect()
            }
            .width(175.dp)
            .height(85.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF22212f))
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Icon(
                    imageVector = if (isSelected.value) Icons.Filled.CheckBox else Icons.Filled.CheckBoxOutlineBlank,
                    contentDescription = "Select Preference",
                    tint = Color.Black,
                    modifier = Modifier.padding(start = 140.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = text, Modifier.padding(bottom = 1.5.dp))
            }
        }
    }
}


private fun updateSelectedPrefs(prefName: String, selected: Boolean, selectedPrefs: MutableList<String>) {
    if (selected) {
        selectedPrefs.add(prefName)
    } else {
        selectedPrefs.remove(prefName)
    }
}
@Composable
@Preview
fun prev(){
//Preferences1()
}
