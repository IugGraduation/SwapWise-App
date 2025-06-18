package com.example.data.source.remote

import com.example.data.model.response.OfferItemDto

interface OfferRemoteDataSource {

    suspend fun getOffer(offerId: String): OfferItemDto

    suspend fun addOffer(
        image: ByteArray,
        name: String,
        place: String,
        details: String,
        categoryUuid: String,
        postUuid: String,
    ): Any

    suspend fun updateOffer(
        image: Byte,
        name: String,
        place: String,
        details: String,
        categoryUuid: String,
        offerUuid: String,
    ): Any

    suspend fun deleteOffer(offerId: String): Any

}