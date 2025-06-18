package com.example.data.model.request

data class VerifyCodeRequest(
    val phone: String,
    val code: String,
    val fcmDevice: String,
    val fcmToken: String
)