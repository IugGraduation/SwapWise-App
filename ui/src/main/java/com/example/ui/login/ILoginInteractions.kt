package com.example.ui.login


interface ILoginInteractions {
    fun onPhoneChange(newValue: String)
    fun onPasswordChange(newValue: String)
    fun onClickLogin()
    fun togglePasswordVisibility()
    fun NavigateToSignup()
}