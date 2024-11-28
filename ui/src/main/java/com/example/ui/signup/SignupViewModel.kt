package com.example.ui.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(): ViewModel() {
    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone = _phone.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _isConfirmPasswordVisible = MutableStateFlow(false)
    val isConfirmPasswordVisible = _isConfirmPasswordVisible.asStateFlow()

    private val _bestBarterSpot = MutableStateFlow("")
    val bestBarterSpot = _bestBarterSpot.asStateFlow()

    private val _bio = MutableStateFlow("")
    val bio = _bio.asStateFlow()


    fun onFullNameChange(newValue: String) {
        _fullName.value = newValue
    }

    fun onPhoneChange(newValue: String) {
        _phone.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
    }

    fun onConfirmPasswordChange(newValue: String) {
        _confirmPassword.value = newValue
    }

    fun toggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.update { !it }
    }

    fun onBestBarterSpotChange(newValue: String) {
        _bestBarterSpot.value = newValue
    }

    fun onBioChange(newValue: String) {
        _bio.value = newValue
    }


    fun onClickSignup(){

    }
}

