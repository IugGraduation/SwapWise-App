package com.example.data.model.response.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val id: String = "",
    val name: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    val email: String? = null,
    var phone: String? = null,
    var place: String? = null,
    val bio: String? = null,
    // val role: String = "member",

    @SerialName("created_at") val createdAt: String? = null,
//    var lastLoginAt: Date? = null,


    val offers: Int? = null,
    val posts: Int? = null,
)