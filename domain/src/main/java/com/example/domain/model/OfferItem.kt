package com.example.domain.model

import com.example.data.model.OfferItemDto

data class OfferItem(
    override val uuid: String = "",
    override val image: String = "",
    override val imgContentDescription: String = "",
    override val user: User = User(),
    override val title: String = "",
    override val details: String = "",
    override val place: String = "",
    override val category: String = "",
    override val date: String = "",

    override val imgResIdError: String? = null,
    override val imgContentDescriptionError: String? = null,
    override val titleError: String? = null,
    override val placeError: String? = null,
    override val detailsError: String? = null,
    override val categoryError: String? = null,
) : IOffer {
    fun toOfferItemDto(): OfferItemDto {
        return OfferItemDto(
            uuid = uuid,
            userName = user.name,
            userImage = user.image,
            image = image,
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
                    image = offerItemDto.userImage.toString(),
                ),
                image = offerItemDto.image.toString(),
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