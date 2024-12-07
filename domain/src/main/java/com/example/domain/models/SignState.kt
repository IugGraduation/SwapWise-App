package com.example.domain.models


data class SignState(
    val fullName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val bestBarterSpot: String = "",
    val bio: String = "",

    val fullNameError: String? = null,
    val phoneError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val bestBarterSpotError: String? = null,
    val bioError: String? = null,
) {
    fun isSuccess(): Boolean {
        return (fullNameError.isNullOrEmpty() && phoneError.isNullOrEmpty() &&
                passwordError.isNullOrEmpty() && confirmPasswordError.isNullOrEmpty() &&
                bestBarterSpotError.isNullOrEmpty() && bioError.isNullOrEmpty())
    }
}