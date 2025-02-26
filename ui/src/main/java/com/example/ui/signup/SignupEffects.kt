package com.example.ui.signup


sealed class SignupEffects {
    data object NavigateToOtp : SignupEffects()
    data object NavigateToLogin : SignupEffects()
}



