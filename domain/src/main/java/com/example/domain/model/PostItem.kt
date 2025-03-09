package com.example.domain.model

import com.example.data.model.response.PostImageDto
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.TopicItemDto


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
//                place = "",
//                category = "",
//                date = "",
//                favoriteCategories ="",
//                rate = "",
//                offers = "",

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
//                place = "",
//                category = "",
                date = postItemDto.date ?: "",
//                favoriteCategories ="",
//                rate = "",
                offers = OfferItem.fromOfferItemDtoList(postItemDto.offers),
            )
        }
    }


    //todo: delete this after implement edit post
    fun toPostItemDto(): PostItemDto {
        return PostItemDto(
            uuid = uuid,
            userImage = user.imageLink,
            userName = user.name,
            userUuid = user.uuid,
            postImages = listOf(PostImageDto(attachment = imageLink, uuid = imageId)),
            postName = title,
            status = if (isOpen) "Active" else "Closed",
            postDetails = details,
//                place = "",
            //todo: add category here
//                category = "",
            date = date,
//                favoriteCategories ="",
//                rate = "",
            offers = OfferItem.toOfferItemDtoList(offers),

            )
    }
}


