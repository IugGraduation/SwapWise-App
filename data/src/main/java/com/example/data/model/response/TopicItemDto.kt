package com.example.data.model.response


import com.google.gson.annotations.SerializedName

data class TopicItemDto(
    @SerializedName("uuid")
    val uuid: String? = null,
    @SerializedName("image")
    val categoryImage: String? = null,
    @SerializedName("num_offers")
    val numOffers: Int? = null,
    @SerializedName("name")
    val categoryName: String? = null,
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
)