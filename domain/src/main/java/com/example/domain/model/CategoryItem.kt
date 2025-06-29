package com.example.domain.model

import com.example.data.model.response.CategoryItemDto
import com.example.data.model.response.TopicItemDto

data class CategoryItem(
    override val uuid: String = "",
    override val title: String = "",
    override val imageLink: String = "",

) : TopicItem() {
    companion object {
        fun fromTopicItemDto(topicItemDto: TopicItemDto): CategoryItem {
            return CategoryItem(
                uuid = topicItemDto.id ?: "",
                title = topicItemDto.categoryName ?: "",
                imageLink = topicItemDto.categoryImage ?: "",
            )
        }

        fun fromCategoryItemDto(categoryItemDto: CategoryItemDto): CategoryItem {
            return CategoryItem(
                uuid = categoryItemDto.categoryUuid ?: "",
                title = categoryItemDto.categoryName ?: "",
            )
        }

        fun fromCategoryItemDtoList(categoryItemDtoList: List<CategoryItemDto?>?): List<CategoryItem> {
            return categoryItemDtoList?.filterNotNull()?.map {
                fromCategoryItemDto(it)
            } ?: listOf()
        }

    }
}
