package com.example.domain.model

import com.example.data.model.response.TopicItemDto

data class CategoryItem(
    override val uuid: String = "",
    override val title: String = "",
    override val imageLink: String = "",

) : TopicItem() {
    companion object {
        fun fromTopicItemDto(topicItemDto: TopicItemDto): CategoryItem {
            return CategoryItem(
                uuid = topicItemDto.uuid ?: "",
                title = topicItemDto.categoryName ?: "",
                imageLink = topicItemDto.categoryImage ?: "",
            )
        }
    }
}
