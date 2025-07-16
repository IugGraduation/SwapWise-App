package com.example.domain.model

import com.example.data.model.response.CategoryItemDto
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.profile.ProfilePostItemDto


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
) : TopicItem() {

    companion object {
        fun fromPostItemDto(postItemDto: PostItemDto): PostItem {
            return PostItem(
                id = postItemDto.id.orEmpty(),
                user = postItemDto.user.toUser(),
                imageUrl = postItemDto.imageUrl.orEmpty(),
                name = postItemDto.name.orEmpty(),
                isOpen = postItemDto.status == "1",
                details = postItemDto.details.orEmpty(),
                place = postItemDto.place.orEmpty(),
                categoryItem = CategoryItem.fromCategoryItemDto(postItemDto.category ?: CategoryItemDto()),
                date = postItemDto.createdAt.orEmpty(),
                favoriteCategoryItems = CategoryItem.fromCategoryItemDtoList(postItemDto.favoriteCategories).toMutableList(),
//                rate = "",
//                offers = OfferItem.fromOfferItemDtoList(postItemDto.offers),
            )
        }
    }
}

fun ProfilePostItemDto.toPostItem(): PostItem {
    return PostItem(
        id = this.uuid.orEmpty(),
        name = this.postName.orEmpty(),
        imageUrl = this.postImage.orEmpty(),
        imageId = this.postImage.orEmpty(),
        user = User(
            uuid = this.userUuid.orEmpty(),
            imageLink = this.userImage.orEmpty(),
            name = this.userName.orEmpty(),
            bio = "",
            phone = "",
            place = "",
            offersNumber = 0,
            postsNumber = 0,
            imgContentDescription = ""
        ),
        place = "",
        details = this.postDetails.orEmpty(),
        categoryItem = CategoryItem(
            id = "",
            name = "",
            imageUrl = ""
        ),
        date = "",
        favoriteCategoryItems = mutableListOf(),
        isOpen = this.status == "Active",
        rate = 0.0f,
        offers = emptyList()
    )
}


