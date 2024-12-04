package com.example.domain

import com.example.domain.models.SignStatus
import javax.inject.Inject

class SignupValidationUseCase @Inject constructor(
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val validateBestBarterSpotUseCase: ValidateBestBarterSpotUseCase,
) {

    operator fun invoke(signStatus: SignStatus): SignStatus {
        val fullNameResult = validateFullNameUseCase(signStatus.fullName)
        val phoneResult = validatePhoneNumberUseCase(signStatus.phone)
        val passwordResult = validatePasswordUseCase(signStatus.password)
        val confirmPasswordResult =
            validateConfirmPasswordUseCase(signStatus.password, signStatus.confirmPassword)
        val bestBarterSpotResult = validateBestBarterSpotUseCase(signStatus.bestBarterSpot)

        val updatedSign = signStatus.copy(
            fullNameError = fullNameResult.exceptionOrNull()?.message,
            phoneError = phoneResult.exceptionOrNull()?.message,
            passwordError = passwordResult.exceptionOrNull()?.message,
            confirmPasswordError = confirmPasswordResult.exceptionOrNull()?.message,
            bestBarterSpotError = bestBarterSpotResult.exceptionOrNull()?.message,
        )
        return updatedSign
    }
}
