package com.example.ui.splash

sealed class SplashEffects {
    data object NavigateToHome : SplashEffects()
    data object NavigateToLogin : SplashEffects()
    data object NavigateToOtp : SplashEffects()
}



