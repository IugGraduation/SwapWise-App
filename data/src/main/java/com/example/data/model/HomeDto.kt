package com.example.data.model


import com.google.gson.annotations.SerializedName

data class HomeDto(
    @SerializedName("data")
    val topicsData: List<TopicDto>? = null,
    @SerializedName("user")
    val user: UserDto? = null,
)