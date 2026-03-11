package com.sam.data.source.remote

import com.sam.data.model.response.PostItemDto
import com.sam.data.util.Constants
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import javax.inject.Inject

class SearchSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : SearchRemoteDataSource {
    override suspend fun search(
        languageCode: String,
        searchText: String,
        categoryIdsFilter: List<String>,
        locationIdsFilter: List<String>
    ): List<PostItemDto> {
        val parameters = buildJsonObject {
            put(Constants.Supabase.Parameters.searchText, searchText)
            put(Constants.Supabase.Parameters.languageCode, languageCode)
            if (categoryIdsFilter.isNotEmpty()) {
                putJsonArray(Constants.Supabase.Parameters.categoryIdsFilter) {
                    categoryIdsFilter.forEach { add(it) }
                }
            }
            if (locationIdsFilter.isNotEmpty()) {
                putJsonArray(Constants.Supabase.Parameters.locationIdsFilter) {
                    locationIdsFilter.forEach { add(it) }
                }
            }
        }


        return supabase.postgrest.rpc(
            function = Constants.Supabase.Functions.searchPosts,
            parameters = parameters
        ).decodeList()
    }


}