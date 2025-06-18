package com.example.data.model.response.profile

import com.example.data.model.response.CategoryItemDto
import com.example.data.model.response.OfferItemDto

data class ProfilePostItemDto(
    val favoriteCategories: List<CategoryItemDto?>? = null,
    val numOffers: Int? = null,
    val offers: List<OfferItemDto?>? = null,
    val postDetails: String? = null,
    val postImage: String? = null,
    val postName: String? = null,
    val status: String? = null,
    val userImage: String? = null,
    val userName: String? = null,
    val userUuid: String? = null,
    val uuid: String? = null,
    val date: String? = null,
)