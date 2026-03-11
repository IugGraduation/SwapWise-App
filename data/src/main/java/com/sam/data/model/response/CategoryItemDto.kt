package com.sam.data.model.response

import com.sam.data.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryItemDto(
    val name: String? = null,
    val id: String? = null,
    @SerialName(Constants.Supabase.Columns.imageUrl) val imageUrl: String? = null,

    )