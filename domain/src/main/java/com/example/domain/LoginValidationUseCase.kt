package com.example.domain

import com.example.domain.exception.InvalidPasswordException
import com.example.domain.exception.InvalidPhoneException
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoginValidationUseCase @Inject constructor() {
    suspend operator fun invoke(
        phone: String,
        password: String,
    ) {
        validatePhone(phone)
        validatePassword(password)
        delay(500)
        //todo: login
    }
}

fun validatePhone(input: String) {
    if (input.length < 3) throw InvalidPhoneException()
}

fun validatePassword(input: String) {
    if (input.length < 6) throw InvalidPasswordException()
}
