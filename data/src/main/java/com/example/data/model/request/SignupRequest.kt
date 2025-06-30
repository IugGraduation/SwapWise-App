package com.example.data.model.request

data class SignupRequest(
    val id: String = "",
    val name: String,
    val phone: String,
    val password: String,
    val confirmPassword: String,
    val place: String
)