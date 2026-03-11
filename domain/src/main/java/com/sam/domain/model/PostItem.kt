package com.sam.domain.model

import com.sam.data.model.response.CategoryItemDto
import com.sam.data.model.response.LocationItemDto
import com.sam.data.model.response.PostItemDto


data class PostItem(
    override val id: String = "",
    override val name: String = "",
    override val imageUrl: String = "",

    val user: User = User(),
    val details: String = "",
    val locationItem: LocationItem = LocationItem(),
    val categoryItem: CategoryItem = CategoryItem(),
    val date: String = "",
    val favoriteCategoryItems: MutableList<CategoryItem> = mutableListOf(),
    val isOpen: Boolean = true,
    val rate: Float = 0f,
) : TopicItem()

fun PostItemDto.toPostItem(): PostItem {
    return PostItem(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        imageUrl = this.imageUrl.orEmpty(),
        user = this.user.toUser(),
        isOpen = this.isActive == true,
        details = this.details.orEmpty(),
        locationItem = LocationItem.fromLocationItemDto(this.location ?: LocationItemDto()),
        categoryItem = CategoryItem.fromCategoryItemDto(this.category ?: CategoryItemDto()),
        date = this.createdAt.orEmpty(),
        favoriteCategoryItems = CategoryItem.fromCategoryItemDtoList(this.favoriteCategories)
            .toMutableList(),
    )
}
