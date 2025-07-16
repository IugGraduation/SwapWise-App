package com.example.data.source.remote

import com.example.data.model.response.PostItemDto
import com.example.data.util.Constants
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import javax.inject.Inject

class SearchSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : SearchRemoteDataSource {
    override suspend fun search(search: String, categoryIds: List<String>?): List<PostItemDto>? {
        val parameters = mutableMapOf<String, Any>(
            "search" to search,
            Constants.Supabase.Parameters.languageCode to "en"
        )
        if (categoryIds != null) {
            parameters[Constants.Supabase.Parameters.categoryIds] = categoryIds
        }

        return supabase.postgrest.rpc(
            function = Constants.Supabase.Functions.searchPosts,
            parameters = Json.encodeToJsonElement(parameters) as JsonObject
        ).decodeList()
    }


}