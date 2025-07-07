package com.example.data.util

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import java.util.UUID
import kotlin.time.Duration.Companion.days

suspend fun SupabaseClient.uploadImageAndGetUrl(
    bucketId: String,
    imageByteArray: ByteArray
): String {
    val fileName = "${UUID.randomUUID()}.jpg"

    storage.from(bucketId).upload(path = fileName, data = imageByteArray) {
        upsert = true
    }

    val imageUrl = storage.from(Constants.Supabase.Buckets.postImages)
        .createSignedUrl(fileName, expiresIn = (365 * 10).days)

    return imageUrl
}