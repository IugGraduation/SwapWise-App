package com.example.data.model.response


import com.google.gson.annotations.SerializedName

data class OfferItemDto(
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
    @SerializedName("date")
    val date: String? = null,
)