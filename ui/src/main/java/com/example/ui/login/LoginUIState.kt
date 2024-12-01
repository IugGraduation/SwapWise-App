package com.example.ui.login

import com.example.domain.models.SignStatus

data class LoginUIState(
    val phone: String = "",
    val password: String = "",

    val phoneError: String? = null,
    val passwordError: String? = null,
) {
    fun toSignStatus() =
        SignStatus(
            phone = phone,
            password = password,
        )

    companion object {
        fun fromSignStatus(signStatus: SignStatus) =
            LoginUIState(
                phone = signStatus.phone,
                password = signStatus.password,

                phoneError = signStatus.phoneError,
                passwordError = signStatus.passwordError,
            )
    }
}
