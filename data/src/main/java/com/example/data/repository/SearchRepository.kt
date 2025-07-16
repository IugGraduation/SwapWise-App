package com.example.data.repository

import com.example.data.source.remote.SearchRemoteDataSource

class SearchRepository(private val searchRemoteDataSource: SearchRemoteDataSource) {

    suspend fun search(search: String, categoriesIds: List<String>?) =
            searchRemoteDataSource.search(
                search = search,
                categoryIds = categoriesIds,
            )
}