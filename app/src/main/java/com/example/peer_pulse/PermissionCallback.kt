package com.example.peer_pulse

interface PermissionCallback {
    fun onPermissionGranted()
    fun onPermissionDenied()
}