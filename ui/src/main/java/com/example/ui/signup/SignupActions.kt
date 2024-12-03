package com.example.ui.signup

data class SignupActions(
    val onFullNameChange: (String) -> Unit,
    val onPhoneChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onConfirmPasswordChange: (String) -> Unit,
    val onBestBarterSpotChange: (String) -> Unit,
    val onBioChange: (String) -> Unit,

    val togglePasswordVisibility: () -> Unit,
    val toggleConfirmPasswordVisibility: () -> Unit,
    val onClickSignup: () -> Unit,
    val onClickGoToLogin: () -> Unit,
)
