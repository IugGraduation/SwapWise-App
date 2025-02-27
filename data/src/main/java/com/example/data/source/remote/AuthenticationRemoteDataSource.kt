package com.example.data.source.remote

import com.example.data.model.request.SignupRequest
import com.example.data.model.response.ApiResponseDto
import com.example.data.model.response.SignupDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationRemoteDataSource {

    @POST("auth/register")
    suspend fun signup(@Body body: SignupRequest): Response<ApiResponseDto<SignupDto>>

}