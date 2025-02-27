package com.example.data.util

import kotlinx.coroutines.delay
import retrofit2.Response


suspend fun <T> fakeCheckResponse(value: T): T? {
    delay(500)
    return value
}


suspend fun <T> checkResponse(function: suspend () -> Response<T>): T? {
    try {
        val result = function()
        when {
            result.isSuccessful -> return result.body()
            else -> throw Exception(result.message())
        }
    } catch (e: Exception) {
        throw e
    }
}
