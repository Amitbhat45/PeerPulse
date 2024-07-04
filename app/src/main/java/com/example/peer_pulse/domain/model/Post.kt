package com.example.peer_pulse.domain.model

data class Post(
    var id: String="",
    var userId: String = "",
    var title: String = "",
    var description: String = "",
    var imageUrl: List<String> = emptyList(),
    var timestamp: Long = 0,
    var likes: Int = 0,
    var preferences : String = "",
    var preferenceId : String = ""
)
