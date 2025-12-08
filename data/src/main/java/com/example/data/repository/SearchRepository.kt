package com.example.data.repository

import com.example.data.source.remote.SearchRemoteDataSource
import kotlinx.coroutines.flow.first

class SearchRepository(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val userRepository: UserRepository
) {

    suspend fun search(search: String, categoriesIds: List<String>?) =
        searchRemoteDataSource.search(
            languageCode = userRepository.getLatestSelectedAppLanguage().first(),
            search = search,
            categoryIds = categoriesIds,
        )
}