package com.example.data.source.remote

import android.net.Uri
import com.example.data.model.response.PostItemDto
import okhttp3.MultipartBody

interface PostRemoteDataSource {

    suspend fun getPost(postId: String): PostItemDto

    suspend fun addPost(
        image: Uri,
        name: String,
        place: String,
        details: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?
    ): Any

    suspend fun updatePost(
        image: Uri,
        name: String,
        place: String,
        details: String,
        categoryUuid: String,
        favoriteCategoryIds: List<String>?,
        postUuid: String,
        status: String,
    ): Any

    suspend fun deletePost(postId: String): Any

}