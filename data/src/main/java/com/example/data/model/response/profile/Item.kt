package com.example.data.model.response.profile


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("bio")
    val bio: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("mobile")
    val mobile: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("offers")
    val offers: Int? = null,
    @SerializedName("place")
    val place: String? = null,
    @SerializedName("posts")
    val posts: Int? = null,
    @SerializedName("uuid")
    val uuid: String? = null
)