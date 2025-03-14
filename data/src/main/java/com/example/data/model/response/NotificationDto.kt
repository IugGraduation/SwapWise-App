package com.example.data.model.response


import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("icon")
    val icon: String? = null,
    @SerializedName("reference_type")
    val referenceType: String? = null,
    @SerializedName("reference_uuid")
    val referenceUuid: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("uuid")
    val uuid: String? = null
)