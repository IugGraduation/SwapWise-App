package com.sam.domain.authentication

import com.sam.data.model.request.SignupRequest
import com.sam.data.repository.AuthRepository
import com.sam.domain.exception.InvalidBestBarterSpotException
import com.sam.domain.exception.InvalidConfirmPasswordException
import com.sam.domain.exception.InvalidFullNameException
import com.sam.domain.exception.PasswordMismatchException
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        fullName: String,
        phone: String,
        password: String,
        confirmPassword: String,
        bestBarterSpotId: String,
    ) {
        validateFullName(fullName)
        validatePhone(phone)
        validatePassword(password)
        validateConfirmPassword(password, confirmPassword)
        validateBestBarterSpot(bestBarterSpotId)

        val signupRequest = SignupRequest(
            phone = phone,
            name = fullName,
            locationId = bestBarterSpotId,
            password = password,
        )
        authRepository.signup(signupRequest)
    }

    private fun validateFullName(input: String) {
        if (input.length < 3) throw InvalidFullNameException()
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String) {
        if (confirmPassword.length < 8) throw InvalidConfirmPasswordException()
        if (confirmPassword != password) throw PasswordMismatchException()
    }

    private fun validateBestBarterSpot(input: String) {
        if (input.length < 3) throw InvalidBestBarterSpotException()
    }

}
