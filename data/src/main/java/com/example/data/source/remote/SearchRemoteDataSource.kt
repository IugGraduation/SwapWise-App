package com.example.data.source.remote

import com.example.data.model.response.TopicItemDto

interface SearchRemoteDataSource {

    suspend fun search(
        search: String,
        categoriesIds: List<String>?
    ): List<TopicItemDto>?

}