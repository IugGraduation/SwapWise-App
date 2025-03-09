package com.example.data.repository

import com.example.data.source.remote.OfferRemoteDataSource
import com.example.data.util.checkResponse
import com.example.data.util.fakeCheckResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class OfferRepository(
    private val offerRemoteDataSource: OfferRemoteDataSource,
) {
    suspend fun getOfferDetails(offerId: String) =
        checkResponse { offerRemoteDataSource.getOffer(offerId) }

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

    suspend fun editOffer(
        image: MultipartBody.Part,
        name: RequestBody,
        place: RequestBody,
        details: RequestBody,
        categoryUuid: RequestBody,
        offerUuid: RequestBody,
    ) =
        checkResponse {
            offerRemoteDataSource.updateOffer(
                image = image,
                name = name,
                place = place,
                details = details,
                categoryUuid = categoryUuid,
                offerUuid = offerUuid,
            )
        }

    suspend fun deleteOffer(offerId: String) =
        fakeCheckResponse(true)
}