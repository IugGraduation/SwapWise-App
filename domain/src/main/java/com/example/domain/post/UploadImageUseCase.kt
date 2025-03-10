package com.example.domain.post

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.domain.exception.FailedToUpdateUserInfoException
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class UploadImageUseCase @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
) {
    operator fun invoke(uri: Uri): RequestBody {
        val contentResolver = applicationContext.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()

        if (bytes != null) {
            Log.e("bk", "UploadImageUseCase bytes: $bytes")
            return bytes.toRequestBody("image/*".toMediaTypeOrNull())
        }

        throw FailedToUpdateUserInfoException()
    }

}
