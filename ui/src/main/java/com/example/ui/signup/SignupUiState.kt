package com.example.ui.signup

import com.example.ui.base.BaseUiState

data class SignupUiState(
    val fullName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val bestBarterSpot: String = "",
    val bio: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,

    val shouldNavigateToHome: Boolean = false,

    val signupError: SignupErrorUiState = SignupErrorUiState(),
    val baseUiState: BaseUiState = BaseUiState()
)

data class SignupErrorUiState(
    val fullNameError: String = "",
    val phoneError: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = "",
    val bestBarterSpotError: String = "",
)
