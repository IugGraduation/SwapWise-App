package com.example.ui.otp

data class OtpUIState(
    val otp: String = "12",
    val isConfirmButtonEnabled: Boolean = false,
    val otpLength: Int = 4,

)