package com.sam.ui.login

import androidx.lifecycle.viewModelScope
import com.sam.domain.authentication.LoginUseCase
import com.sam.domain.exception.InvalidPasswordException
import com.sam.domain.exception.InvalidPhoneException
import com.sam.domain.profile.CustomizeProfileSettingsUseCase
import com.sam.ui.base.BaseViewModel
import com.sam.ui.base.StringsResource
import com.sam.ui.shared.BottomNavigationViewModel
import com.sam.ui.util.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val stringsResource: StringsResource,
    private val customizeProfileSettings: CustomizeProfileSettingsUseCase,
    private val loginUseCase: LoginUseCase,
    private val bottomNavigationViewModel: BottomNavigationViewModel,
) : BaseViewModel<LoginUiState, LoginEffects>(LoginUiState()), ILoginInteractions {

    init {
        viewModelScope.launch { isDarkTheme() }
    }

    private suspend fun isDarkTheme() {
        customizeProfileSettings.isDarkThem().buffer().collect{ isDark ->
            updateData {
                copy(isDarkTheme = isDark)
            }
        }
    }


    override fun onClickLogin() {
        tryToExecute(
            call = {
                loginUseCase(
                    phone = state.value.data.phone,
                    password = state.value.data.password
                )
            },
            onSuccess = { navigateToHome() },
            onError = ::onLoginFail
        )
    }

    private fun navigateToHome() {
        bottomNavigationViewModel.onItemSelected(0)
        sendUiEffect(LoginEffects.NavigateToHome)
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
        updateData {
            copy(
                loginError = LoginErrorUiState(
                    phoneError = phoneError,
                    passwordError = passwordError,
                )
            )
        }
    }

    override fun onPhoneChange(newValue: String) {
        updateFieldError()
        updateData {
            copy(phone = newValue)
        }
    }

    override fun onPasswordChange(newValue: String) {
        updateFieldError()
        updateData {
            copy(password = newValue)
        }
    }

    override fun togglePasswordVisibility() {
        updateData {
            copy(isPasswordVisible = !isPasswordVisible)
        }
    }

    override fun navigateToSignup() {
        sendUiEffect(LoginEffects.NavigateToSignup)
    }
}
