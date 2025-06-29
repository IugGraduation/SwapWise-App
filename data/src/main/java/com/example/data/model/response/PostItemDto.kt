package com.example.data.model.response

import kotlinx.serialization.SerialName

data class PostItemDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("details")
    val details: String? = null,
    @SerialName("is_active")
    val status: String? = null,
    @SerialName("user_id")
    val userId: String? = null,
    @SerialName("location")
    val place: String? = null,
    @SerialName("created_at")
    val date: String? = null,

    //needs work
    val postImages: List<PostImageDto?>? = null,
    val category: CategoryItemDto? = null,
    val favoriteCategories: List<CategoryItemDto?>? = null,

    val userImage: String? = null,
    val userName: String? = null,

    //todo: delete later
    val numOffers: Int? = null,
    val offers: List<OfferItemDto?>? = null,
)