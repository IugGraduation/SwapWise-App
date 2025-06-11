package com.example.domain.post

import android.content.Context
import android.net.Uri
import com.example.domain.exception.EmptyImageException
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class GetImageRequestBodyUseCase @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
) {
    operator fun invoke(uri: Uri, acceptNull: Boolean = false): RequestBody? {
        if (uri.scheme == "http" || uri.scheme == "https") {
            return if (acceptNull) null else throw EmptyImageException()
        }
        val contentResolver = applicationContext.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        //todo: maybe return bytes? a ByteArray

        val imageRequestBody = bytes?.toRequestBody("image/*".toMediaTypeOrNull())
        return imageRequestBody ?: if (acceptNull) null else throw EmptyImageException()
    }
}