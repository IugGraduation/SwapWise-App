package com.example.ui.confirm_number

data class ConfirmNumberUiState(
    val otp: String = "",
    val isConfirmButtonEnabled: Boolean = false,
    val otpLength: Int = 4,
    val shouldNavigateToHome: Boolean = false,
    val isDarkTheme: Boolean = false,
)