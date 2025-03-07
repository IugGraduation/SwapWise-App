package com.example.data.repository

import com.example.data.source.remote.HomeRemoteDataSource
import com.example.data.util.checkResponse

class HomeRepository(
    private val homeRemoteDataSource: HomeRemoteDataSource,
) {
    suspend fun getHomeDto() = checkResponse { homeRemoteDataSource.getHomeDto() }
}
