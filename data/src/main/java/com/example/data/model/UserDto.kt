package com.example.data.model


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("welcome")
    val welcome: String? = null,
)