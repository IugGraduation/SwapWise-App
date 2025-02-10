package com.example.domain

import com.example.data.repository.OfferRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.model.OfferItem
import javax.inject.Inject


class EditOfferUseCase @Inject constructor(
    private val validateOfferUseCase: ValidatePostUseCase,
    private val offerRepository: OfferRepository
) {
    suspend operator fun invoke(offerItem: OfferItem): Boolean {
        validateOfferUseCase(
            title = offerItem.title,
            place = offerItem.place,
            details = offerItem.details
        )
        return offerRepository.editOffer(offerItem.toOfferItemDto()) ?: throw EmptyDataException()
    }
}
