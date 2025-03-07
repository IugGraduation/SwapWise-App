package com.example.data.repository

import com.example.data.model.response.PostItemDto
import com.example.data.source.local.FakePostLocalDataSource
import com.example.data.source.remote.PostRemoteDataSource
import com.example.data.util.checkResponse
import com.example.data.util.fakeCheckResponse

class PostRepository(
    private val fakePostData: FakePostLocalDataSource,
    private val postRemoteDataSource: PostRemoteDataSource,
) {
    suspend fun getPostDetails(uuid: String) =
        checkResponse {postRemoteDataSource.getPost(uuid) }


    suspend fun addPost(postItemDto: PostItemDto) =
        fakeCheckResponse(true)
//        checkResponse(storeApiService::addPost, postItemDto)


    suspend fun updatePost(postItemDto: PostItemDto) =
        fakeCheckResponse(true)
//        checkResponse(storeApiService::updatePost, postItemDto)


    suspend fun deletePost(postId: String) =
        fakeCheckResponse(true)
//        checkResponse(storeApiService::deletePost, postId)


    suspend fun searchPosts(searchValue: String, filterCategories: List<String>) =
        fakeCheckResponse(
            listOf(fakePostData.getPostDetails("").body(), fakePostData.getPostDetails("").body())
        )
}