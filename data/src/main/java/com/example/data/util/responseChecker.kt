package com.example.data.util

import android.util.Log
import com.example.data.model.response.ApiResponseDto
import kotlinx.coroutines.delay
import retrofit2.Response


suspend fun <T> fakeCheckResponse(value: T): T? {
    delay(500)
    return value
}


suspend fun <T> checkResponse(function: suspend () -> Response<ApiResponseDto<T>>): ApiResponseDto<T>? {
    try {
        val result = function()
        when {
            result.isSuccessful -> {
                Log.e("TAG", "Response body: ${result.body()}", )
                if(result.body()?.status == true){
                return result.body()
                }else{
                    Log.e("TAG", "error status Response body: ${result.body()}")
                    throw Exception(result.body()?.message)
                }
            }
            else -> throw Exception(result.message())
        }
    } catch (e: Exception) {
        throw e
    }
}
