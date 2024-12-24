package com.example.data.repository

import com.example.data.model.OfferItemDto
import com.example.data.model.StateDto
import com.example.data.util.wrapWithFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class OfferRepository(
//    private val fakePostLocalDataSource: FakePostLocalDataSource,
) {
    suspend fun getOfferDetails(uuid: String): Flow<StateDto<OfferItemDto?>> =
        wrapWithFlow{Response.success(OfferItemDto())}
}