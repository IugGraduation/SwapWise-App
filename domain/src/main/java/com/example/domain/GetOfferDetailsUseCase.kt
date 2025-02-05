package com.example.domain

import com.example.data.repository.OfferRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.model.OfferItem
import javax.inject.Inject

class GetOfferDetailsUseCase @Inject constructor(private val offerRepository: OfferRepository) {
    suspend operator fun invoke(offerId: String): OfferItem {
        return OfferItem.fromOfferItemDto(
            offerRepository.getOfferDetails(offerId) ?: throw EmptyDataException()
        )
    }
}
