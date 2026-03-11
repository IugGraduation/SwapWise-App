package com.sam.ui.reset_password

import com.sam.ui.base.BaseViewModel

sealed interface ResetPasswordEffect: BaseViewModel.BaseUiEffect{
    data object PopUpToPreviousScreen: ResetPasswordEffect
}