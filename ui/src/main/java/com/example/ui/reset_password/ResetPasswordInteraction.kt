package com.example.ui.reset_password

interface ResetPasswordInteraction {
    fun onPopUpToPreviousScreen()
    fun onCurrentPasswordChange(password: String)
    fun onNewPasswordChange(password: String)
    fun onConfirmNewPasswordChange(password: String)
    fun toggleCurrentPasswordVisibility()
    fun toggleNewPasswordVisibility()
    fun toggleConfirmNewPasswordVisibility()
    fun onResetPasswordClick()
}