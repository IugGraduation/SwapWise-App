package com.example.data.model.response


import com.google.gson.annotations.SerializedName

data class AuthDto(
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("uuid")
    val uuid: String? = null
)