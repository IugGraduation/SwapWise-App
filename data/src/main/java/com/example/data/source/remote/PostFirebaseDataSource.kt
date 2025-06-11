package com.example.data.source.remote

import android.net.Uri
import com.example.data.model.response.PostItemDto

class PostFirebaseDataSource : PostRemoteDataSource {

    override suspend fun getPostDetails(postId: String): PostItemDto {
        TODO()
    }

    override suspend fun addPost(
        imageByteArray: ByteArray,
        name: String,
        place: String,
        details: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?
    ): Any {
        TODO()
    }

    override suspend fun updatePost(
        image: Uri,
        name: String,
        place: String,
        details: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?,
        postId: String,
        status: String,
    ): Any {
        TODO()
    }

    override suspend fun deletePost(postId: String): Any {
        TODO()
    }

}