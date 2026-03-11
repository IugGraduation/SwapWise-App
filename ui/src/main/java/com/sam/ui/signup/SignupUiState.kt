package com.sam.ui.signup

import com.sam.domain.model.LocationItem
import com.sam.ui.models.AsyncState

data class SignupUiState(
    val fullName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val bio: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isDarkTheme: Boolean = false,
    val locationDropdown: AsyncState<List<LocationItem>> = AsyncState.Initial,
    val selectedLocation: LocationItem? = null,
    val signupError: SignupErrorUiState = SignupErrorUiState(),
)

data class SignupErrorUiState(
    val fullNameError: String = "",
    val phoneError: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = "",
    val bestBarterSpotError: String = "",
)
