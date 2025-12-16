package com.example.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LocationItemDto(
    val id: String? = null,
    val name: String? = null
)
