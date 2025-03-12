package com.example.data.model.request

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("current_password")
    val currentPassword: String,
    @SerializedName("password")
    val newPassword: String,
    @SerializedName("confirm_password")
    val confirmNewPassword: String
)