package com.example.peer_pulse.domain.model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.peer_pulse.R
import com.google.rpc.context.AttributeContext

data class Preferences (
    val id: String = "",
    val logo : Int ,
    val background : Int
)




val preferences = listOf(
    "Software Engineering",
    "Electrical Engineering",
    "Civil Engineering",
    "Mechanical Engineering",
    "Architectural Engineering",
    "Sports",
    "Music",
    "Gaming",
    "College Events",
    "Career Development",
    "Tech and Innovation",
    "Complaints and Feedback",
    "Love and Relationship",
)

val trialPreferences  = listOf(
    Preferences(
        id = "Software Engineering",
        logo = R.drawable.download,
        background = R.drawable.software_background
    ),
    Preferences(
        id = "Sports",
        logo = R.drawable.sports_logo,
        background = R.drawable.sports_background
    ),
    Preferences(
        id = "Music",
        logo = R.drawable.music_logo,
        background = R.drawable.music_background
    ),
    Preferences(
        id = "College Events",
        logo =  R.drawable.college_events_logo,
        background = R.drawable.collge_events_background
    ),
)

fun getPreferenceById(id: String): Preferences? {
    return trialPreferences.find { it.id == id }
}
