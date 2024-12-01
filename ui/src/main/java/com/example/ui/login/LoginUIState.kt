package com.example.ui.login

import com.example.domain.models.SignStatus

data class LoginUIState(
    val phone: String = "",
    val password: String = "",

    val phoneError: String? = null,
    val passwordError: String? = null,
){
    fun toSignStatus() =
        SignStatus(
            phone = phone,
            password = password,
        )
}