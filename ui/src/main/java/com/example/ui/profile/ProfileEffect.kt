package com.example.ui.profile

import com.example.ui.base.BaseViewModel

sealed interface ProfileEffect: BaseViewModel.BaseUiEffect {
    data object NavigateToLoginScreen: ProfileEffect
    data object NavigateToResetPassword: ProfileEffect
}