package com.example.domain.offer

import com.example.data.model.response.OfferItemDto
import com.example.data.repository.OfferRepository
import com.example.domain.model.CategoryItem
import com.example.domain.model.OfferItem
import com.example.domain.model.User
import javax.inject.Inject

class GetOfferDetailsUseCase @Inject constructor(private val offerRepository: OfferRepository) {
    suspend operator fun invoke(offerId: String): OfferItem {
        return OfferItem.fromOfferItemDto(
//            offerRepository.getOfferDetails(offerId) ?: throw EmptyDataException()
            OfferItemDto()
        )
    }
}


class GetFakeOfferDetailsUseCase {
    operator fun invoke(): OfferItem {
        val offerItem = OfferItem(
            user = User(
                name = "Cameron Williamson",
                phone = "1231231231"
            ),
            title = "10kg of Sugar Up for 10kg of Rice",
            details = "Looking for a sweet deal? I have 10 kilograms of high-quality sugar that I’d like to exchange for something useful",
            categoryItem = CategoryItem("Category"),
            date = "11/11/11"
        )
        return offerItem
    }
}
