package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("image")
    val imageUrl: String? = null,
    @SerialName("name")
    val name: String? = null,
)