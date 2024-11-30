package com.example.domain

object SignupValidator {
    fun validateFullName(fullName: String): Boolean {
        return fullName.isNotBlank() && fullName.length >= 3
    }

    fun validatePhone(phone: String): Boolean {
        return phone.isNotBlank() && phone.matches(Regex("\\d{10}")) // Example: 10-digit phone
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}
