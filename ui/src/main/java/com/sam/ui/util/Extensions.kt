package com.sam.ui.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import com.sam.domain.exception.EmptyImageException
import java.io.ByteArrayOutputStream

fun String.Companion.empty() = ""

fun String.toByteArray(context: Context): ByteArray? {
    val uri = this.toUri()
    if (uri.scheme != "content") {
        return null
    }

    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)

    val bitmap = BitmapFactory.decodeStream(inputStream) ?: return null

    val outputStream = ByteArrayOutputStream()
    var quality = 100
    // Loop and lower quality until the image is under 1MB
    do {
        outputStream.reset()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        quality -= 5
    } while (outputStream.size() > 1_000_000 && quality > 0)

    bitmap.recycle()

    return outputStream.toByteArray()
}

fun ByteArray?.checkImageNotNull(): ByteArray = this ?: throw EmptyImageException()
