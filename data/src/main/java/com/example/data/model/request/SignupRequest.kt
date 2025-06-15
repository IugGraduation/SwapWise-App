package com.example.data.model.request

data class SignupRequest(
    val email: String,
    val phone: String,
    val name: String,
    val password: String,
    val confirmPassword: String,
    val location: String
)