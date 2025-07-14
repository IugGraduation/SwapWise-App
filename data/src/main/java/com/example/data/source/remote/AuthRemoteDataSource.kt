package com.example.data.source.remote

import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.request.VerifyCodeRequest
import com.example.data.model.response.AuthDto

interface AuthRemoteDataSource {

    suspend fun signup(signupRequest: SignupRequest): AuthDto

    suspend fun login(loginRequest: LoginRequest): AuthDto

    suspend fun verifyCode(verifyCodeRequest: VerifyCodeRequest): AuthDto
}