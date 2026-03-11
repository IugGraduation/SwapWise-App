package com.sam.ui.profile

import com.sam.ui.base.BaseViewModel

sealed interface ProfileEffect: BaseViewModel.BaseUiEffect {
    data object NavigateToLoginScreen: ProfileEffect
    data object NavigateToResetPassword: ProfileEffect
    data class NavigateToPostDetails(val postId: String) : ProfileEffect
}