package com.example.ui.otp

data class OtpUiState(
    val otp: String = "",
    val isConfirmButtonEnabled: Boolean = false,
    val otpLength: Int = 4,
    val isDarkTheme: Boolean = false,
)