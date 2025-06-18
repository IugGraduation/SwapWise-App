package com.example.data.model.response

data class HomeDto(
    val topicsData: List<TopicDto>? = null,
    val userDto: UserDto? = null,
)