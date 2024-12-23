package com.example.domain

import com.example.domain.model.SignState
import javax.inject.Inject

class LoginValidationUseCase @Inject constructor(
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) {

    operator fun invoke(signState: SignState): SignState {
        val phoneResult = validatePhoneNumberUseCase(signState.phone)
        val passwordResult = validatePasswordUseCase(signState.password)

        val updatedSign = signState.copy(
            phoneError = phoneResult.exceptionOrNull()?.message,
            passwordError = passwordResult.exceptionOrNull()?.message
        )
        return updatedSign
    }
}
