package com.example.data.model.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class SignupRequest(
    val name: String,
    val place: String,
    val bio: String? = null,
    @Transient
    val phone: String = "",
    @Transient
    val password: String = "",
)