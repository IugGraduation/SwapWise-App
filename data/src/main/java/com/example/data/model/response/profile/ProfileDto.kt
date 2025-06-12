package com.example.data.model.response.profile

import java.util.Date

data class ProfileDto(
    val uuid: String = "",
    val name: String? = null,
    val image: String? = null,
    val email: String? = null,
    var mobile: String? = null,
    var place: String? = null,
    val bio: String? = null,
    // val role: String = "member",

    val createdAt: Date? = Date(),
    var lastLoginAt: Date? = null,


    val offers: Int? = null,
    val posts: Int? = null,
)