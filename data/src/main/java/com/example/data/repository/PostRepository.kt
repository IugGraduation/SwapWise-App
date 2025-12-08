package com.example.data.repository

import com.example.data.source.remote.PostRemoteDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource,
    private val userRepository: UserRepository
) {
    suspend fun getPostDetails(postId: String) = postRemoteDataSource.getPostDetails(
        userRepository.getLatestSelectedAppLanguage().first(),
        postId
    )

    suspend fun addPost(
        imageByteArray: ByteArray,
        name: String,
        place: String,
        details: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?
    ) = postRemoteDataSource.addPost(
        imageByteArray = imageByteArray,
        name = name,
        place = place,
        details = details,
        categoryId = categoryId,
        favoriteCategoryIds = favoriteCategoryIds
    )

    suspend fun updatePost(
        imageByteArray: ByteArray?,
        name: String,
        place: String,
        details: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?,
        postId: String,
        status: String
    ) = postRemoteDataSource.updatePost(
        imageByteArray = imageByteArray,
        name = name,
        place = place,
        details = details,
        categoryId = categoryId,
        favoriteCategoryIds = favoriteCategoryIds,
        postId = postId,
        status = status
    )

    suspend fun deletePost(postId: String) = postRemoteDataSource.deletePost(postId)
}
