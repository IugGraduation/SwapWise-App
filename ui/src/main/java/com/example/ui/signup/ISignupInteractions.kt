package com.example.ui.signup


interface ISignupInteractions{
    fun onFullNameChange (newValue: String)
    fun onPhoneChange (newValue: String)
    fun onPasswordChange (newValue: String)
    fun onConfirmPasswordChange (newValue: String)
    fun onBestBarterSpotChange (newValue: String)
    fun onBioChange (newValue: String)
    fun togglePasswordVisibility ()
    fun toggleConfirmPasswordVisibility ()
    fun onClickSignup ()
    fun navigateToLogin()
}