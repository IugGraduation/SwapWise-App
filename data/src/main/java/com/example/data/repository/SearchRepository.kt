package com.example.data.repository

import com.example.data.source.remote.SearchRemoteDataSource
import kotlinx.coroutines.flow.first

class SearchRepository(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val userRepository: UserRepository
) {

    suspend fun search(
        search: String,
        categoryIdsFilter: List<String>,
        locationIdsFilter: List<String>
    ) =
        searchRemoteDataSource.search(
            languageCode = userRepository.getLatestSelectedAppLanguage().first(),
            searchText = search,
            categoryIdsFilter = categoryIdsFilter,
            locationIdsFilter = locationIdsFilter,
        )
}