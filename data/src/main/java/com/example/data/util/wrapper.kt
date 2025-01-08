package com.example.data.util

import android.util.Log
import com.example.data.model.StateDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


fun <T> fakeWrapWithFlow(value: T): Flow<StateDto<T>> {
    return flow {
        emit(StateDto.Loading)
        delay(1000)
        emit(StateDto.Success(value))
    }
}


fun <T> wrapWithFlow(function: suspend () -> Response<T>): Flow<StateDto<T?>> {
    return flow {
        emit(StateDto.Loading)
        try {
            val result = function()
            if (result.isSuccessful) {
                emit(StateDto.Success(result.body()))
            } else {
                emit(StateDto.Error(result.message()))
                Log.e("TAG", "Api Error result: ${result.message()}")
            }
        } catch (e: Exception) {
            emit(StateDto.Error(e.message.toString()))
            Log.e("TAG", "Api Error e: ${e.message}")
        }
    }
}


suspend fun <T> fakeCheckResponse(value: T): T? {
    delay(1000)
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

suspend fun <T, B> checkResponse(function: suspend (body: B) -> Response<T>, body: B): T? {
    try {
        val result = function(body)
        when {
            result.isSuccessful -> return result.body()
            else -> throw Exception(result.message())
        }
    } catch (e: Exception) {
        throw e
    }
}
