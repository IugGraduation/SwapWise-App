package com.example.data.model


import com.google.gson.annotations.SerializedName

data class OfferDto(
    @SerializedName("details")
    val details: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("user_image")
    val userImage: String? = null,
    @SerializedName("user_name")
    val userName: String? = null,
    @SerializedName("uuid")
    val uuid: String? = null,
)