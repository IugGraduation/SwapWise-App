package com.example.ui.login

import androidx.lifecycle.ViewModel
import com.example.domain.LoginValidationUseCase
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
    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()


    fun onPhoneChange(newValue: String) {
        _state.update { it.copy(phone = newValue, phoneError = null) }
    }

    fun onPasswordChange(newValue: String) {
        _state.update { it.copy(password = newValue, passwordError = null) }
    }

    fun togglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onClickLogin() {
        if(validateForm()) {
            //login
        }
    }

    private fun validateForm(): Boolean{
        val signStatus = loginValidationUseCase(state.value.toSignState())
        _state.value = LoginUiState.fromSignState(signStatus)
        return signStatus.isSuccess()
    }

}

