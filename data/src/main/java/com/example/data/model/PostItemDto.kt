package com.example.data.model


import com.google.gson.annotations.SerializedName

data class PostItemDto(
    @SerializedName("favorite_categories")
    val favoriteCategories: List<CategoryDto?>? = null,
    @SerializedName("num_offers")
    val numOffers: Int? = null,
    @SerializedName("offers")
    val offers: List<OfferDto?>? = null,
    @SerializedName("post_details")
    val postDetails: String? = null,
    @SerializedName("post_image")
    val postImage: String? = null,
    @SerializedName("post_name")
    val postName: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("user_image")
    val userImage: String? = null,
    @SerializedName("user_name")
    val userName: String? = null,
    @SerializedName("user_uuid")
    val userUuid: String? = null,
    @SerializedName("uuid")
    val uuid: String? = null,
)