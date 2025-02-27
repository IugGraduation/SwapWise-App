package com.example.data.model.response


import com.google.gson.annotations.SerializedName

data class ErrorDto(
    @SerializedName("field_name")
    val fieldName: String? = null,
    @SerializedName("messages")
    val messages: List<String?>? = null
)