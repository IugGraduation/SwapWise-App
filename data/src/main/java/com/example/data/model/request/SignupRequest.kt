package com.example.data.model.request

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    val email: String,

    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("confirm_password")
    val confirmPassword: String
)