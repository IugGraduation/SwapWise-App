package com.example.data.model


import com.google.gson.annotations.SerializedName

data class TopicDto(
    @SerializedName("data")
    val topicItemsDto: List<TopicItemDto>? = null,
    @SerializedName("data_type")
    val title: String? = null,
    @SerializedName("title")
    val unusedTitle: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: String? = null,
)