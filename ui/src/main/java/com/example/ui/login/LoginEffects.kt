package com.example.ui.login

sealed class LoginEffects {
    data object NavigateToHome : LoginEffects()
    data object NavigateToSignup : LoginEffects()
}



