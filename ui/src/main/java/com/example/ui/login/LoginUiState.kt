package com.example.ui.login

import com.example.domain.models.SignState

data class LoginUiState(
    val phone: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,

    val phoneError: String? = null,
    val passwordError: String? = null,
) {
    fun toSignState() =
        SignState(
            phone = phone,
            password = password,
        )

    companion object {
        fun fromSignState(signState: SignState) =
            LoginUiState(
                phone = signState.phone,
                password = signState.password,

                phoneError = signState.phoneError,
                passwordError = signState.passwordError,
            )
    }
}
