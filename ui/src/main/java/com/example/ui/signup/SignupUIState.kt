package com.example.ui.signup

data class SignupUIState(
    val fullName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val bestBarterSpot: String = "",
    val bio: String = "",
    val isConfirmPasswordVisible: Boolean = false
)