package com.example.ui.signup

import com.example.domain.models.SignStatus

data class SignupUIState(
    val fullName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val bestBarterSpot: String = "",
    val bio: String = "",
    val isConfirmPasswordVisible: Boolean = false,

    val fullNameError: String? = null,
    val phoneError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val bestBarterSpotError: String? = null,
    val bioError: String? = null,
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

    companion object {
        fun fromSignStatus(signStatus: SignStatus) =
            SignupUIState(
                fullName = signStatus.fullName,
                phone = signStatus.phone,
                password = signStatus.password,
                confirmPassword = signStatus.confirmPassword,
                bestBarterSpot = signStatus.bestBarterSpot,
                bio = signStatus.bio,

                fullNameError = signStatus.fullNameError,
                phoneError = signStatus.phoneError,
                passwordError = signStatus.passwordError,
                confirmPasswordError = signStatus.confirmPasswordError,
                bestBarterSpotError = signStatus.bestBarterSpotError,
                bioError = signStatus.bioError,
            )
    }
}