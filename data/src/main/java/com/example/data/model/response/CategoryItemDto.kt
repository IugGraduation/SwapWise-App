package com.example.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CategoryItemDto(
    val categoryName: String? = null,
    val categoryUuid: String? = null,
)