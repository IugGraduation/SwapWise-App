package com.sam.domain.model

import com.sam.data.model.response.CategoryItemDto
import com.sam.data.model.response.PostItemDto

data class CategoryItem(
    override val id: String = "",
    override val name: String = "",
    override val imageUrl: String = "",

    ) : TopicItem() {
    companion object {
        fun fromPostItemDto(postItemDto: PostItemDto): CategoryItem {
            return CategoryItem(
                id = postItemDto.id.orEmpty(),
                name = postItemDto.name.orEmpty(),
                imageUrl = postItemDto.imageUrl.orEmpty(),
            )
        }

        fun fromCategoryItemDto(categoryItemDto: CategoryItemDto): CategoryItem {
            return CategoryItem(
                id = categoryItemDto.id.orEmpty(),
                name = categoryItemDto.name.orEmpty(),
            )
        }

        fun fromCategoryItemDtoList(categoryItemDtoList: List<CategoryItemDto?>?): List<CategoryItem> {
            return categoryItemDtoList?.filterNotNull()?.map {
                fromCategoryItemDto(it)
            } ?: listOf()
        }

    }
}
