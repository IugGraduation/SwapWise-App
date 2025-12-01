package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    val name: String? = null,
    val phone: String? = null
)