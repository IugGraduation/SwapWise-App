package com.example.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.example.domain.exception.EmptyImageException

fun String.Companion.empty() = ""

@Composable
fun String.toByteArray(): ByteArray? {
    this.toUri().apply {
        if (scheme == "http" || scheme == "https") {
            return null
        }

        val contentResolver = LocalContext.current.contentResolver
        val inputStream = contentResolver.openInputStream(this)
        return inputStream?.readBytes()
    }
}

fun ByteArray?.checkImageNotNull(): ByteArray {
    if (this == null) throw EmptyImageException() else return this
}