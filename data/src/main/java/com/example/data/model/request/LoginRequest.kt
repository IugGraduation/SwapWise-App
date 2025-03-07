package com.example.data.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("fcm_device")
    val fcmDevice: String,
    @SerializedName("fcm_token")
    val fcmToken: String
)