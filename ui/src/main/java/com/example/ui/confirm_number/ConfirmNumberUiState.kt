package com.example.ui.confirm_number

import com.example.ui.base.BaseUiState

data class ConfirmNumberUiState(
    val otp: String = "12",
    val isConfirmButtonEnabled: Boolean = false,
    val otpLength: Int = 4,
    val shouldNavigateToHome: Boolean = false,

    val baseUiState: BaseUiState = BaseUiState()
)