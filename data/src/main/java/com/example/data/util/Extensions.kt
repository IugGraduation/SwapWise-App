package com.example.data.util

import com.example.data.model.response.ImageDto
import com.example.data.model.response.PostItemDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.request.RpcRequestBuilder
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement

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


suspend fun SupabaseClient.getCategories(): List<PostItemDto> {
    return postgrest.rpc(
        function = Constants.Supabase.Functions.getCategories,
        parameters = Json.encodeToJsonElement(
            //todo: get language from user info, same for anywhere with "en"
            mapOf(Constants.Supabase.Parameters.languageCode to "en")
        ) as JsonObject
    ).decodeList<PostItemDto>()
}


suspend fun SupabaseClient.getRecentPosts(request: RpcRequestBuilder.() -> Unit = {}): List<PostItemDto> {
    return postgrest.rpc(
        function = Constants.Supabase.Functions.getDetailedPosts,
        parameters = Json.encodeToJsonElement(
            mapOf(Constants.Supabase.Parameters.languageCode to "en")
        ) as JsonObject,
        request = request
    ).decodeList<PostItemDto>()
}