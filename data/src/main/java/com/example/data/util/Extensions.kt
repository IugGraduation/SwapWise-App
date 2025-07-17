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
import java.util.UUID
import kotlin.time.Duration.Companion.days

suspend fun SupabaseClient.uploadImageAndGetUrl(
    bucketId: String,
    imageByteArray: ByteArray
): ImageDto {
    val fileName = "${UUID.randomUUID()}.jpg"

    storage.from(bucketId).upload(path = fileName, data = imageByteArray) {
        upsert = true
    }

    val imageUrl = storage.from(Constants.Supabase.Buckets.postImages)
        .createSignedUrl(fileName, expiresIn = (365 * 10).days)

    return ImageDto(imageUrl = imageUrl, imagePath = fileName)
}


suspend fun SupabaseClient.updateImageAndGetUrl(
    bucketId: String,
    imageByteArray: ByteArray
): ImageDto {
    val fileName = "${UUID.randomUUID()}.jpg"

    storage.from(bucketId).update(path = fileName, data = imageByteArray) {
        upsert = true
    }

    val imageUrl = storage.from(Constants.Supabase.Buckets.postImages)
        .createSignedUrl(fileName, expiresIn = (365 * 10).days)

    return ImageDto(imageUrl = imageUrl, imagePath = fileName)
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