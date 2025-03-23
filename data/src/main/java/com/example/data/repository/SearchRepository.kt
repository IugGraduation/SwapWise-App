package com.example.data.repository

import com.example.data.source.remote.SearchRemoteDataSource
import com.example.data.util.checkResponse

class SearchRepository(private val searchRemoteDataSource: SearchRemoteDataSource) {

    suspend fun search(search: String, categoriesIds: Map<String, String>?) =
        checkResponse {
            searchRemoteDataSource.search(
                search = search,
                categoriesIds = categoriesIds,
            )
        }

}