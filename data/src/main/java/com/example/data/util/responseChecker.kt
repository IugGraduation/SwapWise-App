package com.example.data.util
//
//import android.util.Log
//import com.example.data.model.response.ApiResponseDto
//import retrofit2.Response
//import java.net.UnknownHostException
//
//
//suspend fun <T> checkResponse(function: suspend () -> Response<ApiResponseDto<T>>): T? {
//    try {
//        val result = function()
//        when {
//            result.isSuccessful -> {
//                Log.e("TAG", "Response body: ${result.body()}", )
//                if(result.body()?.status == true){
//                return result.body()?.data
//                }else{
//                    throw Exception(result.body()?.message)
//                }
//            }
//            else -> {
//                Log.e("TAG", "checkResponse not successful code: ${result.code()}")
//                Log.e("TAG", "checkResponse not successful code: $result")
//                throw Exception(result.message())
//            }
//        }
//    } catch (e: UnknownHostException) {
//        Log.e("TAG", "checkResponse Internet Error: $e")
//        throw UnknownHostException("Check your Internet Connection")
//    } catch (e: Exception) {
//        Log.e("TAG", "checkResponse Error: $e")
//        throw e
//    }
//}
