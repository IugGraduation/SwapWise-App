package com.sam.data.source.remote

import com.sam.data.model.response.LocationItemDto
import com.sam.data.util.getLocations
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject

class LocationSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient
) : LocationRemoteDataSource {
    override suspend fun getLocations(languageCode: String): List<LocationItemDto> {
        return supabase.getLocations(languageCode)
    }
}
