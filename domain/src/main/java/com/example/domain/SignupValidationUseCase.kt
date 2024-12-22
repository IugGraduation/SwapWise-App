package com.example.domain

import com.example.domain.models.SignState
import com.example.domain.resource.ResourceProvider
import javax.inject.Inject

class SignupValidationUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val validateFullNameUseCase: ValidateAtLeast3CharacterUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val validateBestBarterSpotUseCase: ValidateAtLeast3CharacterUseCase,
) {

    operator fun invoke(signState: SignState): SignState {
        val fullNameResult = validateFullNameUseCase(signState.fullName,
            resourceProvider.getString(R.string.full_name))
        val phoneResult = validatePhoneNumberUseCase(signState.phone)
        val passwordResult = validatePasswordUseCase(signState.password)
        val confirmPasswordResult =
            validateConfirmPasswordUseCase(signState.password, signState.confirmPassword)
        val bestBarterSpotResult = validateBestBarterSpotUseCase(
            signState.bestBarterSpot,
            resourceProvider.getString(R.string.best_barter_spot)
        )

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
