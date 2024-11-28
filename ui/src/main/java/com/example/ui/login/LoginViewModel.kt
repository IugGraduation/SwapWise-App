package com.example.ui.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    private val _phone = MutableStateFlow("")
    val phone = _phone.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    fun onPhoneChange(newValue: String) {
        _phone.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
    }

    fun onClickLogin(){

    }
}

