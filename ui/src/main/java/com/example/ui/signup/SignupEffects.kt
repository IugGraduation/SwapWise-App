package com.example.ui.signup


sealed class SignupEffects {
    data object NavigateToHome : SignupEffects()
    data object NavigateToLogin : SignupEffects()
}



