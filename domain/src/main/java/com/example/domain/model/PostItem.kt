package com.example.domain.model

import com.example.data.model.response.CategoryItemDto
import com.example.data.model.response.PostImageDto
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.TopicItemDto
import com.example.data.model.response.profile.ProfilePostItemDto


data class PostItem(
    override val uuid: String = "",
    override val title: String = "",
    override val imageLink: String = "",

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
        fun fromTopicItemDto(topicItemDto: TopicItemDto): PostItem {
            return PostItem(
                uuid = topicItemDto.uuid ?: "",
                user = User(
                    uuid = topicItemDto.userUuid ?: "",
                    name = topicItemDto.userName ?: "",
                    imageLink = topicItemDto.userImage ?: "",
                ),
                imageLink = topicItemDto.postImage ?: "",
                title = topicItemDto.postName ?: "",
                isOpen = topicItemDto.status == "Active",
                details = topicItemDto.postDetails ?: "",
                offers = List(topicItemDto.numOffers ?: 0){ OfferItem() },
            )
        }

        fun fromPostItemDto(postItemDto: PostItemDto): PostItem {
            val image = postItemDto.postImages?.get(0) ?: PostImageDto()
            return PostItem(
                uuid = postItemDto.uuid ?: "",
                user = User(
                    uuid = postItemDto.userUuid ?: "",
                    name = postItemDto.userName ?: "",
                    imageLink = postItemDto.userImage ?: "",
                ),
                imageId =image.uuid ?: "",
                imageLink =image.attachment ?: "",
                title = postItemDto.postName ?: "",
                isOpen = postItemDto.status == "Active",
                details = postItemDto.postDetails ?: "",
                place = postItemDto.place ?: "",
                categoryItem = CategoryItem.fromCategoryItemDto(postItemDto.category ?: CategoryItemDto()),
                date = postItemDto.date ?: "",
                favoriteCategoryItems = CategoryItem.fromCategoryItemDtoList(postItemDto.favoriteCategories).toMutableList(),
//                rate = "",
                offers = OfferItem.fromOfferItemDtoList(postItemDto.offers),
            )
        }
    }
}

fun ProfilePostItemDto.fromProfilePostItemDto(): PostItem {
    return PostItem(
        uuid = this.uuid ?: "",
        title = this.postName ?: "",
        imageLink = this.postImage ?: "",
        imageId = this.postImage ?: "",
        user = User(
            uuid = this.userUuid ?: "",
            imageLink = this.userImage ?: "",
            name = this.userName ?: "",
            bio = "",
            phone = "",
            place = "",
            offersNumber = 0,
            postsNumber = 0,
            imgContentDescription = ""
        ),
        place = "",
        details = "",
        categoryItem = CategoryItem(
            uuid = "",
            title = "",
            imageLink = ""
        ),
        date = "",
        favoriteCategoryItems = mutableListOf(),
        isOpen = this.status == "Active",
        rate = 0.0f,
        offers = emptyList()
    )
}


