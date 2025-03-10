package com.example.data.model.response


import com.google.gson.annotations.SerializedName

data class PostItemDto(
    @SerializedName("category")
    val category: CategoryItemDto? = null,
    @SerializedName("favorite_categories")
    val favoriteCategories: List<CategoryItemDto?>? = null,
    @SerializedName("num_offers")
    val numOffers: Int? = null,
    @SerializedName("offers")
    val offers: List<OfferItemDto?>? = null,
    @SerializedName("post_details")
    val postDetails: String? = null,
    @SerializedName("post_image")
    val postImages: List<PostImageDto?>? = null,
    @SerializedName("post_name")
    val postName: String? = null,
    @SerializedName("place")
    val place: String? = null,
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
    @SerializedName("date")
    val date: String? = null,
)