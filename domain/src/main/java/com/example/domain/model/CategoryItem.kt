package com.example.domain.model

import com.example.data.model.TopicItemDto

data class CategoryItem(
    override val uuid: String = "",
    override val title: String = "",
    override val imageLink: String = "",
    override val imgContentDescription: String = "",
): TopicItem{
    companion object{
        fun fromTopicItemDto(topicItemDto: TopicItemDto): TopicItem {
            return CategoryItem(
                uuid = topicItemDto.uuid.toString(),
                title = topicItemDto.categoryName.toString(),
                imageLink = topicItemDto.categoryImage.toString(),
            )
        }
    }
}
