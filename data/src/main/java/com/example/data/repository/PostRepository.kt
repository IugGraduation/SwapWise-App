package com.example.data.repository

import com.example.data.model.PostItemDto
import com.example.data.model.StateDto
import com.example.data.source.local.FakePostData
import com.example.data.source.remote.StoreApiService
import com.example.data.util.fakeWrapWithFlow
import com.example.data.util.wrapWithFlow
import kotlinx.coroutines.flow.Flow

class PostRepository(
    private val fakePostData: FakePostData,
    private val storeApiService: StoreApiService,
) {
    suspend fun getPostDetails(uuid: String): Flow<StateDto<PostItemDto?>> =
        wrapWithFlow(fakePostData::getPostDetails, uuid)


    suspend fun addPost(postItemDto: PostItemDto): Flow<StateDto<Boolean?>> =
        wrapWithFlow(storeApiService::addPost, postItemDto)


    suspend fun editPost(postItemDto: PostItemDto): Flow<StateDto<Boolean>> =
        fakeWrapWithFlow(true)


    suspend fun deletePost(postId: String): Flow<StateDto<Boolean>> =
        fakeWrapWithFlow(true)
}