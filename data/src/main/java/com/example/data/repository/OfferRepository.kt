package com.example.data.repository

import com.example.data.model.OfferItemDto
import com.example.data.util.fakeCheckResponse

class OfferRepository(
//    private val fakePostLocalDataSource: FakePostLocalDataSource,
) {
    suspend fun getOfferDetails(offerId: String) =
        fakeCheckResponse(OfferItemDto())


    suspend fun addOffer(postId: String, offerItemDto: OfferItemDto) =
        fakeCheckResponse(true)


    suspend fun editOffer(offerItemDto: OfferItemDto) =
        fakeCheckResponse(true)


    suspend fun deleteOffer(offerId: String) =
        fakeCheckResponse(true)
}