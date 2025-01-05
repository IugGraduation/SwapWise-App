package com.example.data.repository

import com.example.data.model.HomeDto
import com.example.data.model.StateDto
import com.example.data.source.local.FakeHomeData
import com.example.data.util.wrapWithFlow
import kotlinx.coroutines.flow.Flow

class HomeRepository(
    private val fakeHomeLocalDataSource: FakeHomeData
) {

    suspend fun getHomeData(): Flow<StateDto<HomeDto?>> = wrapWithFlow(fakeHomeLocalDataSource::getHomeData)
}