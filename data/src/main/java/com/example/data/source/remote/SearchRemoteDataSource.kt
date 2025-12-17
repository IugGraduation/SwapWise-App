package com.example.data.source.remote

import com.example.data.model.response.PostItemDto

interface SearchRemoteDataSource {
    suspend fun search(
        languageCode: String,
        searchText: String,
        categoryIdsFilter: List<String>,
        locationIdsFilter: List<String>,
    ): List<PostItemDto>
}