package com.example.domain.model

import com.example.data.model.response.CategoryItemDto
import com.example.data.model.response.PostItemDto

data class CategoryItem(
    override val id: String = "",
    override val name: String = "",
    override val imageUrl: String = "",

    ) : TopicItem() {
    companion object {
        fun fromPostItemDto(postItemDto: PostItemDto): CategoryItem {
            return CategoryItem(
                id = postItemDto.id ?: "",
                name = postItemDto.name ?: "",
                imageUrl = postItemDto.imageUrl ?: "",
            )
        }

        fun fromCategoryItemDto(categoryItemDto: CategoryItemDto): CategoryItem {
            return CategoryItem(
                id = categoryItemDto.categoryUuid ?: "",
                name = categoryItemDto.categoryName ?: "",
            )
        }

        fun fromCategoryItemDtoList(categoryItemDtoList: List<CategoryItemDto?>?): List<CategoryItem> {
            return categoryItemDtoList?.filterNotNull()?.map {
                fromCategoryItemDto(it)
            } ?: listOf()
        }

    }
}
