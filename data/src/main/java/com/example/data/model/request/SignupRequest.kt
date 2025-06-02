package com.example.data.model.request

data class SignupRequest(
    val email: String,
    val mobile: String,
    val name: String,
    val password: String,
    val confirmPassword: String,
    val location: String
)