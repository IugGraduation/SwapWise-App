package com.example.ui.login

import com.example.domain.model.ContentState
import com.example.domain.model.SignState

data class LoginUiState(
    val phone: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,

    override val isLoading: Boolean = false,
    override val error: String? = null,

    val phoneError: String? = null,
    val passwordError: String? = null,
): ContentState {
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
