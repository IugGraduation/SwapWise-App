package com.example.data.model.response


import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("category_name")
    val categoryName: String? = null,
    @SerializedName("category_uuid")
    val categoryUuid: String? = null,
    @SerializedName("uuid")
    val uuid: String? = null,
)