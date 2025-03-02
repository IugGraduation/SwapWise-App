package com.example.data.model.request

import com.google.gson.annotations.SerializedName

data class VerifyCodeRequest(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("fcm_device")
    val fcmDevice: String,
    @SerializedName("fcm_token")
    val fcmToken: String
)