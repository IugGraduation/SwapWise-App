package com.example.data.source.remote

import com.example.data.model.response.PostItemDto

interface PostRemoteDataSource {
    suspend fun getPostDetails(languageCode: String, postId: String): PostItemDto

    suspend fun addPost(
        imageByteArray: ByteArray,
        name: String,
        details: String,
        locationId: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?
    ): Any

    suspend fun updatePost(
        imageByteArray: ByteArray?,
        name: String,
        details: String,
        locationId: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?,
        postId: String,
        status: String
    ): Any

    suspend fun deletePost(postId: String): Any
}
