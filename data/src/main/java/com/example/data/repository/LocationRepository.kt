package com.example.data.repository

import com.example.data.model.response.LocationItemDto
import com.example.data.source.remote.LocationRemoteDataSource
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationRemoteDataSource: LocationRemoteDataSource
) {
    suspend fun getLocations(languageCode: String): List<LocationItemDto> {
        return locationRemoteDataSource.getLocations(languageCode)
    }
}
