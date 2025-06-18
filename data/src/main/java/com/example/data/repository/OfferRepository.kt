package com.example.data.repository

import com.example.data.source.remote.OfferRemoteDataSource

class OfferRepository(
    private val offerRemoteDataSource: OfferRemoteDataSource,
) {
    suspend fun getOfferDetails(offerId: String) = true
//        checkResponse { offerRemoteDataSource.getOffer(offerId) }

    suspend fun addOffer(
//        image: MultipartBody.Part,
//        name: RequestBody,
//        place: RequestBody,
//        details: RequestBody,
//        categoryUuid: RequestBody,
//        postUuid: RequestBody,
    ) = true
//        checkResponse {
//            offerRemoteDataSource.addOffer(
//                image = image,
//                name = name,
//                place = place,
//                details = details,
//                categoryUuid = categoryUuid,
//                postUuid = postUuid,
//            )
//        }

    suspend fun editOffer(
//        image: MultipartBody.Part?,
//        name: RequestBody,
//        place: RequestBody,
//        details: RequestBody,
//        categoryUuid: RequestBody,
//        offerUuid: RequestBody,
    ) = true
//        checkResponse {
//            offerRemoteDataSource.updateOffer(
//                image = image,
//                name = name,
//                place = place,
//                details = details,
//                categoryUuid = categoryUuid,
//                offerUuid = offerUuid,
//            )
//        }

    suspend fun deleteOffer(offerId: String) = true
//        checkResponse { offerRemoteDataSource.deleteOffer(offerId) }

}