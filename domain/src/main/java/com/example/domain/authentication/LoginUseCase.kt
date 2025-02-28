package com.example.domain.authentication

import com.example.data.model.request.LoginRequest
import com.example.data.repository.AuthenticationRepository
import com.example.domain.exception.InvalidPasswordException
import com.example.domain.exception.InvalidPhoneException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        phone: String,
        password: String,
    ) {
        validatePhone(phone)
        validatePassword(password)

        val loginRequest = LoginRequest(
            mobile = phone,
            password = password,
            fcmDevice = "android",
            fcmToken = "0"
        )
        authenticationRepository.login(loginRequest)
    }
}

fun validatePhone(input: String) {
    if (input.length < 10) throw InvalidPhoneException()
}

fun validatePassword(input: String) {
    if (input.length < 8) throw InvalidPasswordException()
}
