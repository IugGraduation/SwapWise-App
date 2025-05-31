package com.example.data.source.remote

import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.request.VerifyCodeRequest
import com.example.data.model.response.AuthDto

interface AuthRemoteDataSource {

    suspend fun signup(body: SignupRequest): AuthDto

    suspend fun login(body: LoginRequest): AuthDto

    suspend fun verifyCode(body: VerifyCodeRequest): AuthDto
}