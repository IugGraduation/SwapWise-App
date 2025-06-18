package com.example.data.model.response

data class PostItemDto(
    val category: CategoryItemDto? = null,
    val favoriteCategories: List<CategoryItemDto?>? = null,
    val numOffers: Int? = null,
    val offers: List<OfferItemDto?>? = null,
    val postDetails: String? = null,
    val postImages: List<PostImageDto?>? = null,
    val postName: String? = null,
    val place: String? = null,
    val status: String? = null,
    val userImage: String? = null,
    val userName: String? = null,
    val userUuid: String? = null,
    val uuid: String? = null,
    val date: String? = null,
)