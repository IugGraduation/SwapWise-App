package com.example.ui.confirm_number

import com.example.domain.model.ContentState

data class ConfirmNumberUiState(
    val otp: String = "12",
    val isConfirmButtonEnabled: Boolean = false,
    val otpLength: Int = 4,

    override val isLoading: Boolean = false,
    override val error: String? = null,
) : ContentState