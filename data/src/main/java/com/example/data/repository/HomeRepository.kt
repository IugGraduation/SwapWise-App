package com.example.data.repository

import com.example.data.model.HomeDto
import com.example.data.model.StateDto
import com.example.data.source.local.FakeHomeLocalDataSource
import com.example.data.util.wrapWithFlow
import kotlinx.coroutines.flow.Flow

class HomeRepository(
    private val fakeHomeLocalDataSource: FakeHomeLocalDataSource
) {

    suspend fun getHomeData(): Flow<StateDto<HomeDto?>> = wrapWithFlow(fakeHomeLocalDataSource::getHomeData)
}