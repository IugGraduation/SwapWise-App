package com.example.domain.model

import com.example.data.model.response.CategoryItemDto
import com.example.data.model.response.PostItemDto


data class PostItem(
    override val id: String = "",
    override val name: String = "",
    override val imageUrl: String = "",

    val imageId: String = "",
    val user: User = User(),
    val place: String = "",
    val details: String = "",
    val categoryItem: CategoryItem = CategoryItem(),
    val date: String = "",
    val favoriteCategoryItems: MutableList<CategoryItem> = mutableListOf(),
    val isOpen: Boolean = true,
    val rate: Float = 0f,
    val offers: List<OfferItem> = listOf(),
) : TopicItem()

fun PostItemDto.toPostItem(): PostItem {
    return PostItem(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        imageUrl = this.imageUrl.orEmpty(),
        user = this.user.toUser(),
        isOpen = this.isActive == true,
        details = this.details.orEmpty(),
        place = this.place.orEmpty(),
        categoryItem = CategoryItem.fromCategoryItemDto(this.category ?: CategoryItemDto()),
        date = this.createdAt.orEmpty(),
        favoriteCategoryItems = CategoryItem.fromCategoryItemDtoList(this.favoriteCategories)
            .toMutableList(),
//                rate = "",
//                offers = OfferItem.fromOfferItemDtoList(this.offers),
    )
}


