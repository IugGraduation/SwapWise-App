package com.example.domain.model

import com.example.data.model.PostItemDto
import com.example.data.model.TopicItemDto
import com.example.domain.GetUserUseCase


data class PostItem(
    override val uuid: String = "",
    override val imageLink: String = "",
    override val imgContentDescription: String = "",
    override val user: User = GetUserUseCase()(),
    override val title: String = "",
    override val place: String = "",
    override val details: String = "",
    override val category: String = "",

    override val date: String = "",
    val onClickMakeOffer: () -> Unit = {},
    var onClickGoToDetails: (() -> Unit)? = null,
    val favoriteCategories: MutableList<String> = mutableListOf(),
    val isOpen: Boolean = true,
    val rate: Float = 0f,
    val offers: List<IOffer> = listOf(),

    override val imgResIdError: String? = null,
    override val imgContentDescriptionError: String? = null,
    override val titleError: String? = null,
    override val placeError: String? = null,
    override val detailsError: String? = null,
    override val categoryError: String? = null,
) : IOffer {

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
            status = if(isOpen) "Active" else "Closed",
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


