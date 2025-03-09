package com.example.domain.offer

import com.example.data.repository.OfferRepository
import javax.inject.Inject


class DeleteOfferUseCase @Inject constructor(private val offerRepository: OfferRepository) {
    suspend operator fun invoke(offerId: String) {
        offerRepository.deleteOffer(offerId)
    }
}
