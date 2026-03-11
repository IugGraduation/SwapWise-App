package com.sam.data.source.remote

import com.sam.data.model.response.LocationItemDto

interface LocationRemoteDataSource {
    suspend fun getLocations(languageCode: String): List<LocationItemDto>
}
