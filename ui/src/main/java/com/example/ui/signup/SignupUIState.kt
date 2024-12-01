package com.example.ui.signup

import com.example.domain.models.SignStatus

data class SignupUIState(
    val fullName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val bestBarterSpot: String = "",
    val bio: String = "",
    val isConfirmPasswordVisible: Boolean = false
) {

    fun toSignStatus() =
        SignStatus(
            fullName = fullName,
            phone = phone,
            password = password,
            confirmPassword = confirmPassword,
            bestBarterSpot = bestBarterSpot,
            bio = bio,
        )

}