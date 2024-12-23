package com.example.data.model


import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("errors")
    val errors: List<Any?>? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("pages")
    val pages: Pages? = null,
    @SerializedName("status")
    val status: Boolean? = null,
)