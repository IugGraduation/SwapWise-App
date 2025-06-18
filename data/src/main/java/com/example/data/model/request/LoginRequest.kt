package com.example.data.model.request

data class LoginRequest(
    val phone: String,
    val password: String,
    val fcmDevice: String,
    val fcmToken: String
)