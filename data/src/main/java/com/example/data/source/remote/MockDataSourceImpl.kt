package com.example.data.source.remote

import com.example.data.model.response.ApiResponseDto
import com.example.data.model.response.HomeDto
import com.example.data.model.response.NotificationDto
import com.example.data.model.response.OfferItemDto
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.TopicItemDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class MockDataSourceImpl @Inject constructor() : HomeRemoteDataSource,
    NotificationsRemoteDataSource,
    OfferRemoteDataSource, PostRemoteDataSource, SearchRemoteDataSource {
    override suspend fun getHomeDto(): HomeDto? {
        TODO("Not yet implemented")
    }

    override suspend fun seeAll(type: String): List<TopicItemDto>? {
        TODO("Not yet implemented")
    }

    override suspend fun getPostsFromCategory(categoryId: String): List<TopicItemDto>? {
        TODO("Not yet implemented")
    }

    override suspend fun getNotifications(): List<NotificationDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getOffer(offerId: String): Response<ApiResponseDto<OfferItemDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun addOffer(
        image: MultipartBody.Part,
        name: RequestBody,
        place: RequestBody,
        details: RequestBody,
        categoryUuid: RequestBody,
        postUuid: RequestBody
    ): Response<ApiResponseDto<Any>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateOffer(
        image: MultipartBody.Part?,
        name: RequestBody,
        place: RequestBody,
        details: RequestBody,
        categoryUuid: RequestBody,
        offerUuid: RequestBody
    ): Response<ApiResponseDto<Any>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOffer(offerId: String): Response<ApiResponseDto<Any>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostDetails(postId: String): PostItemDto {
        TODO("Not yet implemented")
    }

    override suspend fun addPost(
        imageByteArray: ByteArray,
        name: String,
        place: String,
        details: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?
    ): Any {
        TODO("Not yet implemented")
    }

    override suspend fun updatePost(
        imageByteArray: ByteArray?,
        name: String,
        place: String,
        details: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?,
        postId: String,
        status: String
    ): Any {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(postId: String): Any {
        TODO("Not yet implemented")
    }

    override suspend fun search(search: String, categoriesIds: List<String>?): List<TopicItemDto>? {
        TODO("Not yet implemented")
    }

}