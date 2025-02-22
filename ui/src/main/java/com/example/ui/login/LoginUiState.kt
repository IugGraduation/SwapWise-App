package com.example.ui.login

data class LoginUiState(
    val phone: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val shouldNavigateToHome: Boolean = false,
    val isDarkTheme: Boolean = false,

    val loginError: LoginErrorUiState = LoginErrorUiState(),
)

data class LoginErrorUiState(
    val phoneError: String = "",
    val passwordError: String = "",
)
