package com.example.data.source.remote

import com.example.data.model.response.PostItemDto
import com.example.data.util.Constants
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
        search: String,
        categoryIds: List<String>?
    ): List<PostItemDto> {
        val parameters = buildJsonObject {
            put(Constants.Supabase.Parameters.searchText, search)
            put(Constants.Supabase.Parameters.languageCode, languageCode)
            if (!categoryIds.isNullOrEmpty()) {
                putJsonArray(Constants.Supabase.Parameters.categoryIdsFilter) {
                    categoryIds.forEach { add(it) }
                }
            }
        }

        return supabase.postgrest.rpc(
            function = Constants.Supabase.Functions.searchPosts,
            parameters = parameters
        ).decodeList()
    }


}