package com.example.data.repository

import com.example.data.model.OfferItemDto
import com.example.data.model.StateDto
import com.example.data.util.wrapWithFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class OfferRepository(
//    private val fakePostLocalDataSource: FakePostLocalDataSource,
) {
    suspend fun getOfferDetails(offerId: String): Flow<StateDto<OfferItemDto?>> =
        wrapWithFlow{Response.success(OfferItemDto())}


    suspend fun addOffer(postId: String, offerItemDto: OfferItemDto): Boolean {
        delay(1000)
        return true
    }


    suspend fun editOffer(offerItemDto: OfferItemDto): Boolean {
        delay(1000)
        return true
    }


    suspend fun deleteOffer(offerId: String): Boolean {
        delay(1000)
        return true
    }
}