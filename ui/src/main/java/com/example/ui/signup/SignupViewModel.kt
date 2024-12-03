package com.example.ui.signup

import androidx.lifecycle.ViewModel
import com.example.domain.SignupValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupValidationUseCase: SignupValidationUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignupUIState())
    val uiState = _uiState.asStateFlow()

    fun onFullNameChange(newValue: String) {
        _uiState.update { it.copy(fullName = newValue, fullNameError = null) }
    }

    fun onPhoneChange(newValue: String) {
        _uiState.update { it.copy(phone = newValue, phoneError = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, passwordError = null) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPassword = newValue, confirmPasswordError = null) }
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
    }

    fun onBestBarterSpotChange(newValue: String) {
        _uiState.update { it.copy(bestBarterSpot = newValue, bestBarterSpotError = null) }
    }

    fun onBioChange(newValue: String) {
        _uiState.update { it.copy(bio = newValue, bioError = null) }
    }

    fun onClickSignup() {
        if (validateForm()) {
            //Signup
        }
    }

    private fun validateForm(): Boolean{
        val signStatus = signupValidationUseCase(uiState.value.toSignStatus())
        _uiState.value = SignupUIState.fromSignStatus(signStatus)
        return signStatus.isSuccess()
    }


}

