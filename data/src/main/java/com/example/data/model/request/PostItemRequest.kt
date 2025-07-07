package com.example.data.model.request

import kotlinx.serialization.SerialName

data class PostItemRequest(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("details")
    val details: String? = null,
    @SerialName("place")
    val place: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("category_id")
    val categoryId: String? = null,
    @SerialName("favorite_category_ids")
    val favoriteCategoryIds: List<String>? = null,
    @SerialName("user_id")
    val userId: String? = null,
)