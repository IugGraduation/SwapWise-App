package com.example.domain.model

import com.example.data.model.response.CategoryItemDto
import com.example.data.model.response.OfferItemDto

data class OfferItem(
    override val id: String = "",
    override val imageUrl: String = "",
    val user: User = User(),
    override val name: String = "",
    val details: String = "",
    val place: String = "",
    val categoryItem: CategoryItem = CategoryItem(),
    val date: String = "",
) : TopicItem() {

    companion object {
        fun fromOfferItemDto(offerItemDto: OfferItemDto): OfferItem {
            return OfferItem(
                id = offerItemDto.uuid ?: "",
                user = User(
                    name = offerItemDto.userName ?: "",
                    imageLink = offerItemDto.userImage ?: "",
                    phone = offerItemDto.phone ?: "",
                    uuid = offerItemDto.userUuid ?: "",
                ),
                imageUrl = offerItemDto.image ?: "",
                name = offerItemDto.title ?: "",
                details = offerItemDto.details ?: "",
                categoryItem = CategoryItem.fromCategoryItemDto(offerItemDto.category ?: CategoryItemDto()),
//                place = "",
                date = offerItemDto.date ?: "",
//                rate = "",
            )
        }

        fun fromOfferItemDtoList(offerItemDtoList: List<OfferItemDto?>?): List<OfferItem> {
            return offerItemDtoList?.filterNotNull()?.map {
                fromOfferItemDto(it)
            } ?: listOf()
        }

    }
}