package com.example.domain.model

import com.example.data.model.response.AuthDto

data class Auth(
    val imageLink: String,
    val name: String,
    val token: String,
    val userId: String
) {
    companion object {
        fun fromAuthDto(authDto: AuthDto): Auth {
            return Auth(
                imageLink = authDto.image ?: "",
                name = authDto.name ?: "",
                token = authDto.token ?: "",
                userId = authDto.uuid ?: ""
            )
        }
    }
}
