package com.example.data.repository

import com.example.data.model.response.HomeDto
import com.example.data.source.local.FakeHomeLocalDataSource

class HomeRepository(
    private val fakeHomeLocalDataSource: FakeHomeLocalDataSource
) {
    suspend fun getHomeData() = HomeDto()//checkResponse(fakeHomeLocalDataSource::getHomeData)
}