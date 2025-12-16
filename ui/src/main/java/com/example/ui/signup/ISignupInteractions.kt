package com.example.ui.signup

import com.example.domain.model.LocationItem


interface ISignupInteractions{
    fun onFullNameChange (newValue: String)
    fun onPhoneChange (newValue: String)
    fun onPasswordChange (newValue: String)
    fun onConfirmPasswordChange (newValue: String)
    fun onBestBarterSpotChange(newValue: LocationItem)
    fun onBioChange (newValue: String)
    fun togglePasswordVisibility ()
    fun toggleConfirmPasswordVisibility ()
    fun onClickSignup ()
    fun navigateToLogin()
}