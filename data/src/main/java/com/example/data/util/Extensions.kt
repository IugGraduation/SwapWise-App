package com.example.data.util

import com.example.data.model.response.ImageDto
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

suspend fun SupabaseClient.getRecentPosts(
    languageCode: String,
    request: RpcRequestBuilder.() -> Unit = {}
): List<PostItemDto> {
    return postgrest.rpc(
        function = Constants.Supabase.Functions.getDetailedPosts,
        parameters = buildJsonObject {
            put(Constants.Supabase.Parameters.languageCode, languageCode)
        },
        request = request
    ).decodeList<PostItemDto>()
}
