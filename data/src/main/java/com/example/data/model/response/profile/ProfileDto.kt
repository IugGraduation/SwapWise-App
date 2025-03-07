package com.example.data.model.response.profile

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("errors")
    val errors: List<Any?>? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("pages")
    val pages: Pages? = null,
    @SerializedName("status")
    val status: Boolean? = null
)