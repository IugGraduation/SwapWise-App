package com.example.data.repository

import com.example.data.model.OfferItemDto
import com.example.data.model.StateDto
import com.example.data.util.fakeCheckResponse
import com.example.data.util.fakeWrapWithFlow
import kotlinx.coroutines.flow.Flow

class OfferRepository(
//    private val fakePostLocalDataSource: FakePostLocalDataSource,
) {
    suspend fun getOfferDetails(offerId: String): Flow<StateDto<OfferItemDto?>> =
        fakeWrapWithFlow(OfferItemDto())


    suspend fun addOffer(postId: String, offerItemDto: OfferItemDto) =
        fakeCheckResponse(true)


    suspend fun editOffer(offerItemDto: OfferItemDto): Flow<StateDto<Boolean>> =
        fakeWrapWithFlow(true)


    suspend fun deleteOffer(offerId: String): Flow<StateDto<Boolean>> =
        fakeWrapWithFlow(true)
}