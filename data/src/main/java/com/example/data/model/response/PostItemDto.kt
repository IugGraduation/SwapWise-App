package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostItemDto(
    val id: String? = null,
    val name: String? = null,
    val details: String? = null,
    val place: String? = null,
    val status: String? = null,
    @SerialName("is_active") val isActive: Boolean? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    val user: UserDto? = null,
    val category: CategoryItemDto? = null,
    @SerialName("favorite_categories") val favoriteCategories: List<CategoryItemDto?>? = null,
)