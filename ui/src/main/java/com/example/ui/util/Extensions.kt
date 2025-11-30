package com.example.ui.util

import android.content.Context
import androidx.core.net.toUri
import com.example.domain.exception.EmptyImageException

fun String.Companion.empty() = ""

fun String.toByteArray(context: Context): ByteArray? {
    val uri = this.toUri()
    if (uri.scheme != "content") {
        return null
    }

    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    return inputStream?.readBytes()
}

fun ByteArray?.checkImageNotNull(): ByteArray {
    if (this == null) throw EmptyImageException() else return this
}
