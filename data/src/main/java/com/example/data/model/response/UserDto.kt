package com.example.data.model.response

import com.example.data.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String? = null,
    @SerialName(Constants.Supabase.Columns.imageUrl)
    val imageUrl: String? = null,
    val name: String? = null,
    val phone: String? = null
)