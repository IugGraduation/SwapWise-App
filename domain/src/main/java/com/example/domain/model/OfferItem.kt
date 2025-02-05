package com.example.domain.model

import com.example.data.model.OfferItemDto

data class OfferItem(
    override val uuid: String = "",
    override val imageLink: String = "",
    val user: User = User(),
    override val title: String = "",
    val details: String = "",
    val place: String = "",
    val category: String = "",
    val date: String = "",
) : TopicItem() {
    fun toOfferItemDto(): OfferItemDto {
        return OfferItemDto(
            uuid = uuid,
            userName = user.name,
            userImage = user.imageLink,
            image = imageLink,
            title = title,
            details = details,
//                place = "",
//                category = "",
//                date = "",
//                favoriteCategories ="",
//                rate = "",
//                offers = "",
        )
    }

    companion object {
        fun fromOfferItemDto(offerItemDto: OfferItemDto): OfferItem {
            return OfferItem(
                uuid = offerItemDto.uuid.toString(),
                user = User(
                    name = offerItemDto.userName.toString(),
                    imageLink = offerItemDto.userImage.toString(),
                ),
                imageLink = offerItemDto.image.toString(),
                title = offerItemDto.title.toString(),
                details = offerItemDto.details.toString(),
//                place = "",
//                category = "",
//                date = "",
//                favoriteCategories ="",
//                rate = "",
//                offers = "",
            )
        }
    }
}