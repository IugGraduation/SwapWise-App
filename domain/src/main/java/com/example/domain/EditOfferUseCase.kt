package com.example.domain

import com.example.data.repository.OfferRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.model.OfferItem
import javax.inject.Inject


class EditOfferUseCase @Inject constructor(private val offerRepository: OfferRepository) {
    suspend operator fun invoke(offerItem: OfferItem): Boolean {
        return offerRepository.editOffer(offerItem.toOfferItemDto()) ?: throw EmptyDataException()
    }
}
