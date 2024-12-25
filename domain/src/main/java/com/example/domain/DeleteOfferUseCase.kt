package com.example.domain

import com.example.data.repository.OfferRepository
import kotlinx.coroutines.delay
import javax.inject.Inject


class DeleteOfferUseCase @Inject constructor(private val offerRepository: OfferRepository) {
    suspend operator fun invoke(offerId: String): Boolean = offerRepository.deleteOffer(offerId)
}
