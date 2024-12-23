package com.example.ui.signup

import com.example.domain.model.SignState

data class SignupUiState(
    val fullName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val bestBarterSpot: String = "",
    val bio: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,

    val fullNameError: String? = null,
    val phoneError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val bestBarterSpotError: String? = null,
    val bioError: String? = null,
) {

    fun toSignState() =
        SignState(
            fullName = fullName,
            phone = phone,
            password = password,
            confirmPassword = confirmPassword,
            bestBarterSpot = bestBarterSpot,
            bio = bio,
        )

    companion object {
        fun fromSignState(signState: SignState) =
            SignupUiState(
                fullName = signState.fullName,
                phone = signState.phone,
                password = signState.password,
                confirmPassword = signState.confirmPassword,
                bestBarterSpot = signState.bestBarterSpot,
                bio = signState.bio,

                fullNameError = signState.fullNameError,
                phoneError = signState.phoneError,
                passwordError = signState.passwordError,
                confirmPasswordError = signState.confirmPasswordError,
                bestBarterSpotError = signState.bestBarterSpotError,
                bioError = signState.bioError,
            )
    }
}