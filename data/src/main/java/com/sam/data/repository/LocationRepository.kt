package com.sam.data.repository

import com.sam.data.model.response.LocationItemDto
import com.sam.data.source.remote.LocationRemoteDataSource
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationRemoteDataSource: LocationRemoteDataSource
) {
    suspend fun getLocations(languageCode: String): List<LocationItemDto> {
        return locationRemoteDataSource.getLocations(languageCode)
    }
}
