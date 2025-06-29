package com.example.domain.model

import com.example.data.model.response.TopicDto


data class TopicsHolder(
    val title: String = "",
    val items: List<TopicItem> = listOf(),
    val url: String = ""
)


fun TopicDto.toTopicsHolder(): TopicsHolder {
    val items = topicItemDtos?.map { topicItemDto ->
        if (title == "Categories") {
            CategoryItem.fromTopicItemDto(topicItemDto)
        } else {
            PostItem.fromTopicItemDto(topicItemDto)
        }

    }
    return TopicsHolder(
        title = title.toString(),
        items = items ?: listOf(),
        url = url.toString()
    )
}