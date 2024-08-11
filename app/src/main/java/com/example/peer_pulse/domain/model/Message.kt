package com.example.peer_pulse.domain.model

import android.net.Uri

data class Message(
    var messageId : String = "",
    var text: String = "",
    var userId: String = "",
    var imageUrl: String? = null,
    var collegeCode : String = "",
    var communityName : String = "",
    var timeStamp: Long = 0,
    var userName : String = ""
)

