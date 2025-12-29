package com.example.ui.signup

import androidx.lifecycle.viewModelScope
import com.example.domain.authentication.SignupUseCase
import com.example.domain.exception.InvalidBestBarterSpotException
import com.example.domain.exception.InvalidConfirmPasswordException
import com.example.domain.exception.InvalidFullNameException
import com.example.domain.exception.InvalidPasswordException
import com.example.domain.exception.InvalidPhoneException
import com.example.domain.exception.PasswordMismatchException
import com.example.domain.location.GetLocationsUseCase
import com.example.domain.model.LocationItem
import com.example.domain.profile.CustomizeProfileSettingsUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.StringsResource
import com.example.ui.shared.BottomNavigationViewModel
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
        customizeProfileSettings.isDarkThem().buffer().collect{ isDark ->
            updateData {
                copy(isDarkTheme = isDark)
            }
        }
    }

    private fun getLocations() {
        updateData {
            copy(
                locationDropdown = locationDropdown.copy(
                    isLoading = true,
                    error = null
                )
            )
        }
        tryToExecute(
            call = { getLocationsUseCase() },
            shouldLoad = false,
            onSuccess = { locations ->
                updateData {
                    copy(
                        locationDropdown = locationDropdown.copy(
                            items = locations,
                            isLoading = false
                        )
                    )
                }
            },
            onError = { throwable ->
                updateData {
                    copy(
                        locationDropdown = locationDropdown.copy(
                            isLoading = false,
                            error = throwable.message
                        )
                    )
                }
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
                    bestBarterSpotId = state.value.data.locationDropdown.selectedItem?.id.orEmpty(),
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
        updateData { copy(locationDropdown = locationDropdown.copy(selectedItem = newValue)) }
    }

    override fun onBioChange(newValue: String) {
        updateFieldError()
        updateData {
            copy(bio = newValue)
        }
    }
}
