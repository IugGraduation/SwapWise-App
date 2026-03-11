package com.sam.data.model.response.profile

import com.sam.data.model.response.CategoryItemDto
import com.sam.data.model.response.UserDto
import kotlinx.serialization.SerialName

data class ProfilePostItemDto(
    val id: String? = null,
    val name: String? = null,
    val details: String? = null,
    @SerialName("is_active") val isActive: Boolean? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    val user: UserDto? = null,

    val userImage: String? = null,
    val userName: String? = null,
    val userUuid: String? = null,
    @SerialName("favorite_categories") val favoriteCategories: List<CategoryItemDto?>? = null,
)