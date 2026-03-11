package com.sam.data.source.remote

import com.sam.data.model.response.PostItemDto

interface SearchRemoteDataSource {
    suspend fun search(
        languageCode: String,
        searchText: String,
        categoryIdsFilter: List<String>,
        locationIdsFilter: List<String>,
    ): List<PostItemDto>
}