package com.example.domain.model

import com.example.data.model.response.TopicDto


data class TopicsHolder(
    val title: String = "",
    val items: List<TopicItem> = listOf(),
    val url: String = ""
) {
    companion object {
        fun fromTopicDto(topicDto: TopicDto): TopicsHolder {
            val items = topicDto.topicItemsDto?.map { topicItemDto ->
                if (topicDto.title == "Categories" || topicDto.url == "category") {
                    CategoryItem.fromTopicItemDto(topicItemDto)
                } else {
                    PostItem.fromTopicItemDto(topicItemDto)
                }

            }
            return TopicsHolder(
                title = topicDto.title.toString(),
                items = items ?: listOf(),
                url = topicDto.url.toString()
            )
        }
    }
}