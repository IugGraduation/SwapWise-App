package com.sam.data.model.response

data class HomeDto(
    val topicDtos: List<TopicDto>? = null,
    val userDto: UserDto? = null,
)