package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostItemDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("details")
    val details: String? = null,
    @SerialName("is_active")
    val status: String? = null,
    @SerialName("place")
    val place: String? = null,
    @SerialName("created_at")
    val created_at: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("category")
    val category: CategoryItemDto? = null,
    @SerialName("favorite_categories")
    val favoriteCategories: List<CategoryItemDto?>? = null,
    @SerialName("user")
    val user: UserDto? = null,


    //todo: delete later
    val numOffers: Int? = null,
    val offers: List<OfferItemDto?>? = null,
)