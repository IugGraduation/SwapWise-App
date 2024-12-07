package com.example.domain

import com.example.domain.models.SignState
import javax.inject.Inject

class SignupValidationUseCase @Inject constructor(
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val validateBestBarterSpotUseCase: ValidateBestBarterSpotUseCase,
) {

    operator fun invoke(signState: SignState): SignState {
        val fullNameResult = validateFullNameUseCase(signState.fullName)
        val phoneResult = validatePhoneNumberUseCase(signState.phone)
        val passwordResult = validatePasswordUseCase(signState.password)
        val confirmPasswordResult =
            validateConfirmPasswordUseCase(signState.password, signState.confirmPassword)
        val bestBarterSpotResult = validateBestBarterSpotUseCase(signState.bestBarterSpot)

        val updatedSign = signState.copy(
            fullNameError = fullNameResult.exceptionOrNull()?.message,
            phoneError = phoneResult.exceptionOrNull()?.message,
            passwordError = passwordResult.exceptionOrNull()?.message,
            confirmPasswordError = confirmPasswordResult.exceptionOrNull()?.message,
            bestBarterSpotError = bestBarterSpotResult.exceptionOrNull()?.message,
        )
        return updatedSign
    }
}
