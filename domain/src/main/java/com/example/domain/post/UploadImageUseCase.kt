package com.example.domain.post

import android.content.Context
import android.net.Uri
import com.example.domain.exception.EmptyImageException
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class UploadImageUseCase @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
) {
    private var imageRequestBody:  RequestBody? = null

    operator fun invoke(uri: Uri) {
        val contentResolver = applicationContext.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: return

        imageRequestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())
    }

    fun getImageRequestBody(acceptNull: Boolean = false): RequestBody? {
        return imageRequestBody ?: if (acceptNull) null else throw EmptyImageException()
    }
}
