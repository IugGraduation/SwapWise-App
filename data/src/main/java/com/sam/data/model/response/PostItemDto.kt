package com.sam.data.model.response

import com.sam.data.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostItemDto(
    val id: String? = null,
    val name: String? = null,
    val details: String? = null,
//    @SerialName("category_id") val categoryId: String? = null,
    @SerialName(Constants.Supabase.Columns.isActive)
    val isActive: Boolean? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName(Constants.Supabase.Columns.imageUrl) val imageUrl: String? = null,
    val user: UserDto? = null,
    val location: LocationItemDto? = null,
    val category: CategoryItemDto? = null,
    @SerialName("favorite_categories") val favoriteCategories: List<CategoryItemDto?>? = null,
)
