package com.example.data.repository

import com.example.data.source.local.FakeHomeLocalDataSource
import com.example.data.util.checkResponse

class HomeRepository(
    private val fakeHomeLocalDataSource: FakeHomeLocalDataSource
) {
    suspend fun getHomeData() = checkResponse(fakeHomeLocalDataSource::getHomeData)
}