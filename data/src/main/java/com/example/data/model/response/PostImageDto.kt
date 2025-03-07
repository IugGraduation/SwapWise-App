package com.example.data.model.response


import com.google.gson.annotations.SerializedName

data class PostImageDto(
    @SerializedName("attachment")
    val attachment: String? = null,
    @SerializedName("uuid")
    val uuid: String? = null
)