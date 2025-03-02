package com.example.data.source.remote

import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.request.VerifyCodeRequest
import com.example.data.model.response.ApiResponseDto
import com.example.data.model.response.AuthDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRemoteDataSource {

    @POST("auth/register")
    suspend fun signup(@Body body: SignupRequest): Response<ApiResponseDto<AuthDto>>

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): Response<ApiResponseDto<AuthDto>>

    @POST("auth/verify_code")
    suspend fun verifyCode(@Body body: VerifyCodeRequest): Response<ApiResponseDto<AuthDto>>

}