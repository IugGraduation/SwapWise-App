package com.example.data.model.response

data class TopicDto(
    val topicItemDtos: List<TopicItemDto>? = null,
    val title: String? = null,
    val url: String? = null,
)