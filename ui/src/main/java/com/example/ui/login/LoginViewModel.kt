package com.example.ui.login

import androidx.lifecycle.ViewModel
import com.example.domain.LoginValidationUseCase
import com.example.ui.login.LoginUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginValidationUseCase: LoginValidationUseCase,
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState = _uiState.asStateFlow()


    fun onPhoneChange(newValue: String) {
        _uiState.update { it.copy(phone = newValue, phoneError = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, passwordError = null) }
    }

    fun onClickLogin() {
        if(validateForm()) {
            //login
        }
    }

    private fun validateForm(): Boolean{
        val signStatus = loginValidationUseCase(uiState.value.toSignStatus())
        _uiState.value = LoginUIState.fromSignStatus(signStatus)
        return signStatus.isSuccess()
    }

}

