package com.example.domain.authentication

import com.example.data.model.request.SignupRequest
import com.example.data.repository.AuthenticationRepository
import com.example.domain.exception.InvalidBestBarterSpotException
import com.example.domain.exception.InvalidConfirmPasswordException
import com.example.domain.exception.InvalidFullNameException
import com.example.domain.exception.PasswordMismatchException
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
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

        val signupRequest = SignupRequest(
            mobile = phone,
            name = fullName,
            password = password,
            confirmPassword = confirmPassword
        )
        authenticationRepository.signup(signupRequest)
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
