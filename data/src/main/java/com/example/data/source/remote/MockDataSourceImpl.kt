package com.example.data.source.remote

import com.example.data.model.response.HomeDto
import com.example.data.model.response.NotificationDto
import com.example.data.model.response.OfferItemDto
import com.example.data.model.response.PostItemDto
import javax.inject.Inject

class MockDataSourceImpl @Inject constructor() : HomeRemoteDataSource,
    NotificationsRemoteDataSource,
    OfferRemoteDataSource, PostRemoteDataSource, SearchRemoteDataSource {
    override suspend fun getHomeDto(languageCode: String): HomeDto? {
        TODO("Not yet implemented")
    }

    override suspend fun seeAll(languageCode: String, type: String): List<PostItemDto>? {
        TODO("Not yet implemented")
    }

    override suspend fun getPostsFromCategory(
        languageCode: String,
        categoryId: String
    ): List<PostItemDto>? {
        TODO("Not yet implemented")
    }

    override suspend fun getNotifications(): List<NotificationDto> {
        TODO("Not yet implemented")
    }


    override suspend fun getPostDetails(languageCode: String, postId: String): PostItemDto {
        TODO("Not yet implemented")
    }

    override suspend fun addPost(
        imageByteArray: ByteArray,
        name: String,
        locationId: String,
        details: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?
    ): Any {
        TODO("Not yet implemented")
    }

    override suspend fun updatePost(
        imageByteArray: ByteArray?,
        name: String,
        locationId: String,
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

    override suspend fun search(
        languageCode: String,
        searchText: String,
        categoryIdsFilter: List<String>,
        locationIdsFilter: List<String>
    ): List<PostItemDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getOffer(offerId: String): OfferItemDto {
        TODO("Not yet implemented")
    }

    override suspend fun addOffer(
        image: ByteArray,
        name: String,
        place: String,
        details: String,
        categoryUuid: String,
        postUuid: String
    ): Any {
        TODO("Not yet implemented")
    }

    override suspend fun updateOffer(
        image: Byte,
        name: String,
        place: String,
        details: String,
        categoryUuid: String,
        offerUuid: String
    ): Any {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOffer(offerId: String): Any {
        TODO("Not yet implemented")
    }

}