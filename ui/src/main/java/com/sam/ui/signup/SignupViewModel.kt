package com.sam.ui.signup

import androidx.lifecycle.viewModelScope
import com.sam.domain.authentication.SignupUseCase
import com.sam.domain.exception.InvalidBestBarterSpotException
import com.sam.domain.exception.InvalidConfirmPasswordException
import com.sam.domain.exception.InvalidFullNameException
import com.sam.domain.exception.InvalidPasswordException
import com.sam.domain.exception.InvalidPhoneException
import com.sam.domain.exception.PasswordMismatchException
import com.sam.domain.location.GetLocationsUseCase
import com.sam.domain.model.LocationItem
import com.sam.domain.profile.CustomizeProfileSettingsUseCase
import com.sam.ui.base.BaseViewModel
import com.sam.ui.base.StringsResource
import com.sam.ui.shared.BottomNavigationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val stringsResource: StringsResource,
    private val customizeProfileSettings: CustomizeProfileSettingsUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val signupUseCase: SignupUseCase,
    private val bottomNavigationViewModel: BottomNavigationViewModel,
) : BaseViewModel<SignupUiState, SignupEffects>(SignupUiState()), ISignupInteractions {

    init {
        viewModelScope.launch { isDarkTheme() }
        getLocations()
    }

    private suspend fun isDarkTheme() {
        customizeProfileSettings.isDarkThem().buffer().collect { isDark ->
            updateData {
                copy(isDarkTheme = isDark)
            }
        }
    }

    private fun getLocations() {
        tryToExecuteAsync(
            call = { getLocationsUseCase() },
            stateUpdater = { newState ->
                updateData { copy(locationDropdown = newState) }
            }
        )
    }


    override fun onClickSignup() {
        tryToExecute(
            call = {
                signupUseCase(
                    fullName = state.value.data.fullName,
                    phone = state.value.data.phone,
                    password = state.value.data.password,
                    confirmPassword = state.value.data.confirmPassword,
                    bestBarterSpotId = state.value.data.selectedLocation?.id.orEmpty(),
                )
            },
            onSuccess = { navigateToHome() },
            onError = ::onSignupFail
        )
    }

    override fun navigateToLogin() {
        sendUiEffect(SignupEffects.NavigateToLogin)
    }

    override fun onRetryLocations() {
        getLocations()
    }

    private fun navigateToHome() {
        bottomNavigationViewModel.onItemSelected(0)
        sendUiEffect(SignupEffects.NavigateToHome)
    }

    private fun onSignupFail(throwable: Throwable) {
        when (throwable) {
            is InvalidFullNameException -> {
                updateFieldError(fullNameError = stringsResource.invalidUsername)
            }

            is InvalidPhoneException -> {
                updateFieldError(phoneError = stringsResource.invalidPhoneNumber)
            }

            is InvalidPasswordException -> {
                updateFieldError(passwordError = stringsResource.invalidPassword)
            }

            is InvalidConfirmPasswordException -> {
                updateFieldError(confirmPasswordError = stringsResource.invalidPassword)
            }

            is InvalidBestBarterSpotException -> {
                updateFieldError(bestBarterSpotError = stringsResource.invalidBestBarterSpot)
            }

            is PasswordMismatchException -> {
                updateFieldError(
                    passwordError = stringsResource.passwordMismatch,
                    confirmPasswordError = stringsResource.passwordMismatch
                )
            }

            else -> onActionFail(throwable)
        }
    }

    private fun updateFieldError(
        fullNameError: String = "",
        phoneError: String = "",
        passwordError: String = "",
        confirmPasswordError: String = "",
        bestBarterSpotError: String = "",
    ) {
        updateData {
            copy(
                signupError = SignupErrorUiState(
                    fullNameError = fullNameError,
                    phoneError = phoneError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError,
                    bestBarterSpotError = bestBarterSpotError,
                )
            )
        }
    }


    override fun onFullNameChange(newValue: String) {
        updateFieldError()
        updateData {
            copy(fullName = newValue)
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

    override fun onConfirmPasswordChange(newValue: String) {
        updateFieldError()
        updateData {
            copy(confirmPassword = newValue)
        }
    }

    override fun toggleConfirmPasswordVisibility() {
        updateData {
            copy(isConfirmPasswordVisible = !isConfirmPasswordVisible)
        }
    }

    override fun onBestBarterSpotChange(newValue: LocationItem) {
        updateFieldError()
        updateData { copy(selectedLocation = newValue) }
    }

    override fun onBioChange(newValue: String) {
        updateFieldError()
        updateData {
            copy(bio = newValue)
        }
    }
}
