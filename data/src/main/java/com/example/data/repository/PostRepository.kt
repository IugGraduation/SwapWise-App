package com.example.data.repository

import com.example.data.model.response.PostItemDto
import com.example.data.source.remote.PostRemoteDataSource
import com.example.data.util.checkResponse
import com.example.data.util.fakeCheckResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostRepository(private val postRemoteDataSource: PostRemoteDataSource) {

    suspend fun getPostDetails(postId: String) =
        checkResponse { postRemoteDataSource.getPost(postId) }

    suspend fun addPost(
        images: List<MultipartBody.Part>?,
        name: RequestBody,
        place: RequestBody,
        details: RequestBody,
        categoryUuid: RequestBody,
        fcategory: List<MultipartBody.Part>?
    ) =
        checkResponse {
            postRemoteDataSource.addPost(
                images = images,
                name = name,
                place = place,
                details = details,
                categoryUuid = categoryUuid,
                fcategory = fcategory
            )
        }

    suspend fun updatePost(
        images: List<MultipartBody.Part>?,
        name: RequestBody,
        place: RequestBody,
        details: RequestBody,
        categoryUuid: RequestBody,
        fcategory: List<MultipartBody.Part>?,
        postUuid: RequestBody,
        status: RequestBody,
    ) =
        checkResponse {
            postRemoteDataSource.updatePost(
                images = images,
                name = name,
                place = place,
                details = details,
                categoryUuid = categoryUuid,
                fcategory = fcategory,
                postUuid = postUuid,
                status = status
            )
        }

    suspend fun deletePost(postId: String) =
        checkResponse { postRemoteDataSource.deletePost(postId) }


    suspend fun searchPosts(searchValue: String, filterCategoryIds: List<String>) =
        fakeCheckResponse(
            listOf(PostItemDto(), PostItemDto())
        )
}