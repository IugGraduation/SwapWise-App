package com.sam.domain.authentication

import com.sam.data.model.request.LoginRequest
import com.sam.data.repository.AuthRepository
import com.sam.domain.exception.InvalidPasswordException
import com.sam.domain.exception.InvalidPhoneException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        phone: String,
        password: String,
    ) {
        validatePhone(phone)
        validatePassword(password)

        val loginRequest = LoginRequest(
            phone = phone,
            password = password,
            fcmDevice = "android",
            fcmToken = "0"
        )
        authRepository.login(loginRequest)
    }
}

fun validatePhone(input: String) {
    if (input.length < 10) throw InvalidPhoneException()
}

fun validatePassword(input: String) {
    if (input.length < 8) throw InvalidPasswordException()
}
