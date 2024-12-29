package com.example.data.repository

import com.example.data.model.PostItemDto
import com.example.data.model.StateDto
import com.example.data.source.local.FakePostData
import com.example.data.util.wrapWithFlow
import kotlinx.coroutines.flow.Flow

class PostRepository(
    private val fakePostLocalDataSource: FakePostData,
) {
    suspend fun getPostDetails(uuid: String): Flow<StateDto<PostItemDto?>> =
        wrapWithFlow(fakePostLocalDataSource::getPostDetails, uuid)
}