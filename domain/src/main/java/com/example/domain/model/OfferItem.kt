package com.example.domain.model

import com.example.data.model.response.OfferItemDto

data class OfferItem(
    override val uuid: String = "",
    override val imageLink: String = "",
    val user: User = User(),
    override val title: String = "",
    val details: String = "",
    val place: String = "",
    val categoryItem: CategoryItem = CategoryItem(),
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
                date = date,
//                rate = "",
        )
    }

    companion object {
        fun fromOfferItemDto(offerItemDto: OfferItemDto): OfferItem {
            return OfferItem(
                uuid = offerItemDto.uuid ?: "",
                user = User(
                    name = offerItemDto.userName ?: "",
                    imageLink = offerItemDto.userImage ?: "",
                ),
                imageLink = offerItemDto.image ?: "",
                title = offerItemDto.title ?: "",
                details = offerItemDto.details ?: "",
//                place = "",
//                category = "",
                date = offerItemDto.date ?: "",
//                rate = "",
            )
        }

        fun fromOfferItemDtoList(offerItemDtoList: List<OfferItemDto?>?): List<OfferItem> {
            return offerItemDtoList?.filterNotNull()?.map {
                fromOfferItemDto(it)
            } ?: listOf()
        }

        fun toOfferItemDtoList(offerItemList: List<OfferItem>): List<OfferItemDto> {
            return offerItemList.map {
                it.toOfferItemDto()
            }
        }
    }
}