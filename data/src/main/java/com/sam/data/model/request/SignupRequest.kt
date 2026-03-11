package com.sam.data.model.request

import com.sam.data.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class SignupRequest(
    val name: String,
    @SerialName(Constants.Supabase.Columns.locationId)
    val locationId: String,
    val bio: String? = null,
    @Transient
    val phone: String = "",
    @Transient
    val password: String = "",
)
