package com.example.domain

import com.example.data.repository.OfferRepository
import com.example.domain.exception.EmptyDataException
import javax.inject.Inject


class DeleteOfferUseCase @Inject constructor(private val offerRepository: OfferRepository) {
    suspend operator fun invoke(offerId: String): Boolean {
        return offerRepository.deleteOffer(offerId) ?: throw EmptyDataException()

    }
}
