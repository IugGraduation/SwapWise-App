package com.example.domain.model

import com.example.data.model.OfferItemDto
import com.example.data.model.PostItemDto

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

    override val isLoading: Boolean = false,
    override val error: String? = null,

    override val imgResIdError: String? = null,
    override val imgContentDescriptionError: String? = null,
    override val titleError: String? = null,
    override val placeError: String? = null,
    override val detailsError: String? = null,
    override val categoryError: String? = null,
) : IOffer, ContentState {
    fun isSuccess(): Boolean {
        return (imgResIdError.isNullOrEmpty() && imgContentDescriptionError.isNullOrEmpty() &&
                titleError.isNullOrEmpty() && placeError.isNullOrEmpty() &&
                detailsError.isNullOrEmpty() && categoryError.isNullOrEmpty())
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
//                onClickMakeOffer = "",
//                onClickGoToDetails = "",
//                date = "",
//                favoriteCategories ="",
//                rate = "",
//                offers = "",

            )
        }
    }
}