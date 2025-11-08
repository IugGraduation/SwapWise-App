package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryItemDto(
    val name: String? = null,
    val id: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,

    )