package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicItemDto(
    @SerialName("id")
    val id: String? = null,
    val categoryImage: String? = null,
    val numOffers: Int? = null,
    val categoryName: String? = null,
    val postDetails: String? = null,
    val postImage: String? = null,
    val postName: String? = null,
    val status: String? = null,
    val userImage: String? = null,
    val userName: String? = null,
    val userUuid: String? = null,
)