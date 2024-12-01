package com.example.domain

import com.example.domain.models.SignStatus
import javax.inject.Inject

class SignupValidationUseCase @Inject constructor(
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) {

    operator fun invoke(signStatus: SignStatus): SignStatus {
        val phoneResult = validatePhoneNumberUseCase(signStatus.phone)
        val passwordResult = validatePasswordUseCase(signStatus.password)

        val updatedSign = signStatus.copy(
            phoneError = phoneResult.exceptionOrNull()?.message,
            passwordError = passwordResult.exceptionOrNull()?.message
        )
        return updatedSign
    }
}
