package com.example.ui.signup

import com.example.domain.model.LocationItem

data class SignupUiState(
    val fullName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val locations: List<LocationItem> = emptyList(),
    val bestBarterSpot: LocationItem? = null,
    val bio: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isDarkTheme: Boolean = false,
    val signupError: SignupErrorUiState = SignupErrorUiState(),
)

data class SignupErrorUiState(
    val fullNameError: String = "",
    val phoneError: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = "",
    val bestBarterSpotError: String = "",
)
