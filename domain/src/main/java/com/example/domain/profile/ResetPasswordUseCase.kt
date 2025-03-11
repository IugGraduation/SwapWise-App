package com.example.domain.profile

import com.example.data.repository.UserRepository
import com.example.domain.exception.EmptyConfirmPasswordException
import com.example.domain.exception.EmptyNewPasswordException
import com.example.domain.exception.EmptyPasswordException
import com.example.domain.exception.InvalidConfirmPasswordException
import com.example.domain.exception.InvalidNewPasswordException
import com.example.domain.exception.InvalidPasswordException
import com.example.domain.exception.PasswordMismatchException
import com.example.domain.exception.SamePasswordException
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(
        currentPassword: String,
        newPassword: String,
        confirmNewPassword: String
    ) {
        if (currentPassword.isBlank()) throw EmptyPasswordException()
        if (newPassword.isBlank()) throw EmptyNewPasswordException()
        if (confirmNewPassword.isBlank()) throw EmptyConfirmPasswordException()

        if (validatePassword(currentPassword)) throw InvalidPasswordException()
        if (validatePassword(newPassword)) throw InvalidNewPasswordException()
        if (validatePassword(confirmNewPassword)) throw InvalidConfirmPasswordException()

        if (isSamePassword(currentPassword, newPassword)) throw SamePasswordException()
        if (!isSamePassword(newPassword, confirmNewPassword)) throw PasswordMismatchException()

         userRepository.resetPassword(
            currentPassword = currentPassword,
            newPassword = newPassword,
            confirmNewPassword = confirmNewPassword
        )
    }

    private fun validatePassword(password: String): Boolean {
        return password.length < 8 ||
               !password.any { it.isUpperCase() } ||
               !password.any { it.isDigit() } ||
               !password.any { it in "!@#\$%^&*()-_+=<>?/" }
    }


    private fun isSamePassword(password1: String, password2: String) = password1 == password2



}