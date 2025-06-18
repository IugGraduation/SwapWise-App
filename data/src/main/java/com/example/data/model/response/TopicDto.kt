package com.example.data.model.response

data class TopicDto(
    val topicItemsDto: List<TopicItemDto>? = null,
    val title: String? = null,
    val unusedTitle: String? = null,
    val type: String? = null,
    val url: String? = null,
)