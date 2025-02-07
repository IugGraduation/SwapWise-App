package com.example.domain

import com.example.domain.exception.InvalidBestBarterSpotException
import com.example.domain.exception.InvalidConfirmPasswordException
import com.example.domain.exception.InvalidFullNameException
import com.example.domain.exception.PasswordMismatchException
import kotlinx.coroutines.delay
import javax.inject.Inject

class SignupValidationUseCase @Inject constructor(
) {
    suspend operator fun invoke(
        fullName: String,
        phone: String,
        password: String,
        confirmPassword: String,
        bestBarterSpot: String,
    ) {
        validateFullName(fullName)
        validatePhone(phone)
        validatePassword(password)
        validateConfirmPassword(password, confirmPassword)
        validateBestBarterSpot(bestBarterSpot)
        delay(500)
        //todo: signup
    }

    private fun validateFullName(input: String) {
        if (input.length < 3) throw InvalidFullNameException()
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String) {
        if (confirmPassword.length < 3) throw InvalidConfirmPasswordException()
        if (confirmPassword != password) throw PasswordMismatchException()
    }

    private fun validateBestBarterSpot(input: String) {
        if (input.length < 3) throw InvalidBestBarterSpotException()
    }

}
