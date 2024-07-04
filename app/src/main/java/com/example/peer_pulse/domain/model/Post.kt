package com.example.peer_pulse.domain.model

data class Post(
    var id: String="",
    val userId: String,
    val title: String,
    val description: String,
    val imageUrl: List<String>,
    val timestamp: String,
    val likes: Int,
    val preferences : String,
    val preferenceId : String
)
