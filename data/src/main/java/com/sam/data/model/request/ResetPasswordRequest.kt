package com.sam.data.model.request

data class ResetPasswordRequest(
    val currentPassword: String,
    val newPassword: String,
    val confirmNewPassword: String
)