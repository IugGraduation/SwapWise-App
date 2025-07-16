package com.example.data.source.remote

import com.example.data.model.response.PostItemDto

interface SearchRemoteDataSource {

    suspend fun search(
        search: String,
        categoryIds: List<String>?
    ): List<PostItemDto>?

}