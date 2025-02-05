package com.example.data.repository

import com.example.data.model.PostItemDto
import com.example.data.source.local.FakePostData
import com.example.data.source.remote.StoreApiService
import com.example.data.util.checkResponse
import com.example.data.util.fakeCheckResponse

class PostRepository(
    private val fakePostData: FakePostData,
    private val storeApiService: StoreApiService,
) {
    suspend fun getPostDetails(uuid: String) =
        checkResponse(fakePostData::getPostDetails, uuid)
//        wrapWithFlow(storeApiService::getPost, uuid)


    suspend fun addPost(postItemDto: PostItemDto) =
        fakeCheckResponse(true)
//        wrapWithFlow(storeApiService::addPost, postItemDto)


    suspend fun updatePost(postItemDto: PostItemDto) =
        fakeCheckResponse(true)
//        wrapWithFlow(storeApiService::updatePost, postItemDto)


    suspend fun deletePost(postId: String) =
        fakeCheckResponse(true)
//        wrapWithFlow(storeApiService::deletePost, postId)


    suspend fun searchPosts(searchValue: String, filterCategories: List<String>) =
        fakeCheckResponse(
            listOf(fakePostData.getPostDetails("").body(), fakePostData.getPostDetails("").body())
        )
}