package com.example.data.repository

import com.example.data.model.PostItemDto
import com.example.data.model.StateDto
import com.example.data.source.local.FakePostLocalDataSource
import com.example.data.util.wrapWithFlow
import kotlinx.coroutines.flow.Flow

class PostRepository(
    private val fakePostLocalDataSource: FakePostLocalDataSource,
) {
    suspend fun getPostDetails(uuid: String): Flow<StateDto<PostItemDto?>> =
        wrapWithFlow(fakePostLocalDataSource::getPostDetails, uuid)
}