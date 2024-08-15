package com.example.peer_pulse.domain.model

data class Reply(
    var id : String = "",
    var content : String = "",
    var userId: String = "",
    var postId: String = "",
    var timeStamp: Long = 0,
    var college : String = "",
    var collegeLogo : Int = 0,
    var likes : Int = 0,
)
