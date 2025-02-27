package com.example.data.model.response


import com.google.gson.annotations.SerializedName

data class ApiResponseDto<T>(
    @SerializedName("status")
    val status: Boolean? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("pages")
    val pages: PagesDto? = null,
    @SerializedName("errors")
    val errors: List<ErrorDto?>? = null,
)