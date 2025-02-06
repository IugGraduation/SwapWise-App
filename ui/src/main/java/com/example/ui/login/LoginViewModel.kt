package com.example.ui.login

import com.example.domain.LoginValidationUseCase
import com.example.domain.exception.InvalidPasswordException
import com.example.domain.exception.InvalidPhoneException
import com.example.ui.base.BaseViewModel
import com.example.ui.base.StringsResource
import com.example.ui.util.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val stringsResource: StringsResource,
    private val loginValidationUseCase: LoginValidationUseCase,
) : BaseViewModel<LoginUiState>(LoginUiState()), ILoginInteractions {

    override fun onClickLogin() {
        tryToExecute(
            call = {
                loginValidationUseCase(
                    phone = state.value.phone,
                    password = state.value.password
                )
            },
            onSuccess = { navigateToHome() },
            onError = ::onLoginFail
        )
    }

    private fun navigateToHome() {
        _state.update { it.copy(shouldNavigateToHome = true) }
    }

    private fun onLoginFail(throwable: Throwable) {
        when (throwable) {
            is InvalidPhoneException -> {
                updateFieldError(phoneError = stringsResource.invalidPhoneNumber)
            }

            is InvalidPasswordException -> {
                updateFieldError(passwordError = stringsResource.invalidPassword)
            }

            else -> onActionFail(throwable)
        }
    }


    private fun updateFieldError(
        phoneError: String = String.empty(),
        passwordError: String = String.empty(),
    ) {
        _state.update {
            it.copy(
                loginError = LoginErrorUiState(
                    phoneError = phoneError,
                    passwordError = passwordError,
                )
            )
        }
    }

    override fun onPhoneChange(newValue: String) {
        updateFieldError()
        _state.update { it.copy(phone = newValue) }
    }

    override fun onPasswordChange(newValue: String) {
        updateFieldError()
        _state.update { it.copy(password = newValue) }
    }

    override fun togglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

}

