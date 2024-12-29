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


fun <T, B> wrapWithFlow(
    function: suspend (body: B) -> Response<T>,
    body: B,
): Flow<StateDto<T?>> {
    return flow {
        emit(StateDto.Loading)
        try {
            val result = function(body)
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