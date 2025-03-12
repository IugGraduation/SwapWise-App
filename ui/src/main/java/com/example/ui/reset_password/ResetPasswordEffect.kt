package com.example.ui.reset_password

import com.example.ui.base.BaseViewModel

sealed interface ResetPasswordEffect: BaseViewModel.BaseUiEffect{
    data object PopUpToPreviousScreen: ResetPasswordEffect
}