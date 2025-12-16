package com.example.data.source.remote

import com.example.data.model.response.LocationItemDto

interface LocationRemoteDataSource {
    suspend fun getLocations(languageCode: String): List<LocationItemDto>
}
