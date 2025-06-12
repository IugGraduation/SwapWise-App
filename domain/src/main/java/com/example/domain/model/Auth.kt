package com.example.domain.model

import com.example.data.model.response.AuthDto

data class Auth(
    val imageLink: String,
    val name: String,
    val token: String,
    val userId: String
)


fun AuthDto.toAuth(): Auth {
    return Auth(
        imageLink = image ?: "",
        name = name ?: "",
        token = token ?: "",
        userId = uuid ?: ""
    )
}