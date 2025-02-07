package com.example.domain.model

import com.example.data.model.PostItemDto
import com.example.data.model.TopicItemDto


data class PostItem(
    override val uuid: String = "",
    override val title: String = "",
    override val imageLink: String = "",

    val user: User = User(),
    val place: String = "",
    val details: String = "",
    val category: String = "",
    val date: String = "",
    val onClickMakeOffer: () -> Unit = {},
    var onClickGoToDetails: () -> Unit = {},
    val favoriteCategories: MutableList<String> = mutableListOf(),
    val isOpen: Boolean = true,
    val rate: Float = 0f,
    val offers: List<OfferItem> = listOf(),
) : TopicItem() {

    companion object {
        fun fromTopicItemDto(topicItemDto: TopicItemDto): TopicItem {
            return PostItem(
                uuid = topicItemDto.uuid.toString(),
                user = User(
                    uuid = topicItemDto.userUuid.toString(),
                    name = topicItemDto.userName.toString(),
                    imageLink = topicItemDto.userImage.toString(),
                ),
                imageLink = topicItemDto.postImage.toString(),
                title = topicItemDto.postName.toString(),
                isOpen = topicItemDto.status.toString() == "Active",
                details = topicItemDto.postDetails.toString(),
//                place = "",
//                category = "",
//                onClickMakeOffer = "",
//                onClickGoToDetails = "",
//                date = "",
//                favoriteCategories ="",
//                rate = "",
//                offers = "",

            )
        }

        fun fromPostItemDto(postItemDto: PostItemDto): PostItem {
            return PostItem(
                uuid = postItemDto.uuid.toString(),
                user = User(
                    uuid = postItemDto.userUuid.toString(),
                    name = postItemDto.userName.toString(),
                    imageLink = postItemDto.userImage.toString(),
                ),
                imageLink = postItemDto.postImage.toString(),
                title = postItemDto.postName.toString(),
                isOpen = postItemDto.status.toString() == "Active",
                details = postItemDto.postDetails.toString(),
//                place = "",
//                category = "",
//                onClickMakeOffer = "",
//                onClickGoToDetails = "",
//                date = "",
//                favoriteCategories ="",
//                rate = "",
//                offers = "",

            )
        }
    }


    fun toPostItemDto(): PostItemDto {
        return PostItemDto(
            uuid = uuid,
            userImage = user.imageLink,
            userName = user.name,
            userUuid = user.uuid,
            postImage = imageLink,
            postName = title,
            status = if (isOpen) "Active" else "Closed",
            postDetails = details,
//                place = "",
//                category = "",
//                onClickMakeOffer = "",
//                onClickGoToDetails = "",
//                date = "",
//                favoriteCategories ="",
//                rate = "",
//                offers = "",

        )
    }
}


