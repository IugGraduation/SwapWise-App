package com.sam.data.model.response

data class TopicDto(
    val topicItemDtos: List<PostItemDto>? = null,
    val title: String? = null,
    val url: String? = null,
)