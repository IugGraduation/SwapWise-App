package com.example.data.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,

    @SerializedName("password")
    val password: String,
    @SerializedName("fcm_device")
    val fcmDevice: String,
    @SerializedName("fcm_token")
    val fcmToken: String
)