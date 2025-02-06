package com.example.ui.login

import com.example.ui.base.BaseUiState

data class LoginUiState(
    val phone: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val shouldNavigateToHome: Boolean = false,

    val loginError: LoginErrorUiState = LoginErrorUiState(),
    val baseUiState: BaseUiState = BaseUiState()
)

data class LoginErrorUiState(
    val phoneError: String = "",
    val passwordError: String = "",
)
