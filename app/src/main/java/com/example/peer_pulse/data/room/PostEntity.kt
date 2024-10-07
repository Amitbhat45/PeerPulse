package com.example.peer_pulse.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class post(
    @PrimaryKey val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val images: List<String> = emptyList(),
    val timestamp: Long = 0L,
    val likes: Int = 0,
    val preferences: String = "",
    val preferencesId: String = "",
    val collegeCode: String? = null
)