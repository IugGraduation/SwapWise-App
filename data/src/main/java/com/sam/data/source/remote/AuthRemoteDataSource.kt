package com.sam.data.source.remote

import com.sam.data.model.request.LoginRequest
import com.sam.data.model.request.SignupRequest
import com.sam.data.model.request.VerifyCodeRequest
import com.sam.data.model.response.AuthDto

interface AuthRemoteDataSource {

    suspend fun signup(signupRequest: SignupRequest): AuthDto

    suspend fun login(loginRequest: LoginRequest): AuthDto

    suspend fun verifyCode(verifyCodeRequest: VerifyCodeRequest): AuthDto
}