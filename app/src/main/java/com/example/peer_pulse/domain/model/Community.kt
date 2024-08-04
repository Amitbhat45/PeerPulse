package com.example.peer_pulse.domain.model

import com.example.peer_pulse.R

data class Community(
    var name : String = "",
    var description : String = "",
    var messageIds : List<String> = emptyList(),
)
