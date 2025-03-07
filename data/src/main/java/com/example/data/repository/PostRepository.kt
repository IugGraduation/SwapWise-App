package com.example.data.repository

import com.example.data.model.response.PostItemDto
import com.example.data.source.remote.PostRemoteDataSource
import com.example.data.util.checkResponse
import com.example.data.util.fakeCheckResponse

class PostRepository(private val postRemoteDataSource: PostRemoteDataSource) {

    suspend fun getPostDetails(uuid: String) = checkResponse { postRemoteDataSource.getPost(uuid) }

    suspend fun addPost(postItemDto: PostItemDto) =
        checkResponse { postRemoteDataSource.addPost(postItemDto) }

    suspend fun updatePost(postItemDto: PostItemDto) =
        checkResponse { postRemoteDataSource.updatePost(postItemDto) }


    suspend fun deletePost(postId: String) =
        checkResponse { postRemoteDataSource.deletePost(postId) }


    suspend fun searchPosts(searchValue: String, filterCategories: List<String>) =
        fakeCheckResponse(
            listOf(PostItemDto(), PostItemDto())
        )
}