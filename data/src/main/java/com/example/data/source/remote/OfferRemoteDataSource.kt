package com.example.data.source.remote

import com.example.data.model.response.ApiResponseDto
import com.example.data.model.response.OfferItemDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface OfferRemoteDataSource {

    @GET("offer/{offer_id}")
    suspend fun getOffer(@Path("offer_id") offerId: String): Response<ApiResponseDto<OfferItemDto>>

    @Multipart
    @POST("offer/store")
    suspend fun addOffer(
        @Part image: MultipartBody.Part,
        @Part("title") name: RequestBody,
        @Part("place") place: RequestBody,
        @Part("details") details: RequestBody,
        @Part("category_uuid") categoryUuid: RequestBody,
        @Part("post_uuid") postUuid: RequestBody,
    ): Response<ApiResponseDto<Any>>

    @Multipart
    @POST("offer/update")
    suspend fun updateOffer(
        @Part image: MultipartBody.Part?,
        @Part("title") name: RequestBody,
        @Part("place") place: RequestBody,
        @Part("details") details: RequestBody,
        @Part("category_uuid") categoryUuid: RequestBody,
        @Part("offer_uuid") offerUuid: RequestBody,
    ): Response<ApiResponseDto<Any>>

    @DELETE("offer/{offer_id}/delete")
    suspend fun deleteOffer(@Path("offer_id") offerId: String): Response<ApiResponseDto<Any>>

}