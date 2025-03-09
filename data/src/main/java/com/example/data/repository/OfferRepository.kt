package com.example.data.repository

import com.example.data.model.response.OfferItemDto
import com.example.data.source.remote.OfferRemoteDataSource
import com.example.data.util.checkResponse
import com.example.data.util.fakeCheckResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class OfferRepository(
    private val offerRemoteDataSource: OfferRemoteDataSource,
) {
    suspend fun getOfferDetails(offerId: String) =
        fakeCheckResponse(OfferItemDto())


    suspend fun addOffer(
        image: MultipartBody.Part,
        name: RequestBody,
        place: RequestBody,
        details: RequestBody,
        categoryUuid: RequestBody,
        postUuid: RequestBody,
    ) =
        checkResponse {
            offerRemoteDataSource.addOffer(
                image = image,
                name = name,
                place = place,
                details = details,
                categoryUuid = categoryUuid,
                postUuid = postUuid,
            )
        }

    suspend fun editOffer(offerItemDto: OfferItemDto) =
        fakeCheckResponse(true)


    suspend fun deleteOffer(offerId: String) =
        fakeCheckResponse(true)
}