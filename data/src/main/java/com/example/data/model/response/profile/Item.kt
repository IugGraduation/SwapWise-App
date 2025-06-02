package com.example.data.model.response.profile

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date


data class Item(
    val uuid: String = "", // Matches Firebase Auth UID and Document ID
    val name: String? = null,
    val image: String? = null,
    val email: String? = null,
    var mobile: String? = null,
    var place: String? = null,
    val bio: String? = null,
    // val role: String = "member",

    @ServerTimestamp
    val createdAt: Date? = null,
    var lastLoginAt: Date? = null,


    val offers: Int? = null,
    val posts: Int? = null,
)