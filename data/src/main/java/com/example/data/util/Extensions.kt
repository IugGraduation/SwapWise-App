package com.example.data.util

import com.example.data.model.response.ImageDto
import com.example.data.model.response.LocationItemDto
import com.example.data.model.response.PostItemDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.request.RpcRequestBuilder
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

suspend fun SupabaseClient.uploadAndGetPublicUrl(
    bucketId: String,
    imagePath: String,
    imageByteArray: ByteArray
): ImageDto {
    storage.from(bucketId).upload(path = imagePath, data = imageByteArray) {
        upsert = true
    }
    val imageUrl = storage.from(bucketId).publicUrl(imagePath)
    return ImageDto(imageUrl = imageUrl, imagePath = imagePath)
}

suspend fun SupabaseClient.getCategories(languageCode: String): List<PostItemDto> {
    return postgrest.rpc(
        function = Constants.Supabase.Functions.getCategories,
        parameters = buildJsonObject {
            put(Constants.Supabase.Parameters.languageCode, languageCode)
        }
    ).decodeList<PostItemDto>()
}

suspend fun SupabaseClient.getLocations(languageCode: String): List<LocationItemDto> {
    return postgrest.rpc(
        function = Constants.Supabase.Functions.getLocations,
        parameters = buildJsonObject {
            put(Constants.Supabase.Parameters.languageCode, languageCode)
        }
    ).decodeList<LocationItemDto>()
}

suspend fun SupabaseClient.getRecentPosts(
    languageCode: String,
    showActiveOnly: Boolean = true,
    request: RpcRequestBuilder.() -> Unit = {}
): List<PostItemDto> {
    return postgrest.rpc(
        function = Constants.Supabase.Functions.getDetailedPosts,
        parameters = buildJsonObject {
            put(Constants.Supabase.Parameters.languageCode, languageCode)
        },
        request = {
            if (showActiveOnly) {
                filter {
                    eq(
                        Constants.Supabase.Columns.isActive, true
                    )
                }
            }
            // This applies any additional filters passed from the call site
            request()
        }
    ).decodeList<PostItemDto>()
}
