package com.example.data.model.response

data class OfferItemDto(
    val category: CategoryItemDto? = null,
    val details: String? = null,
    val image: String? = null,
    val title: String? = null,
    val userImage: String? = null,
    val userName: String? = null,
    val userUuid: String? = null,
    val uuid: String? = null,
    val phone: String? = null,
    val date: String? = null,
)