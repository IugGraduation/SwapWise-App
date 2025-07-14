package com.example.data.repository

import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.request.VerifyCodeRequest
import com.example.data.model.response.AuthDto
import com.example.data.source.local.AuthLocalDataSource
import com.example.data.source.remote.AuthRemoteDataSource

class AuthRepository(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) {
    suspend fun signup(signupRequest: SignupRequest) {
        val authDto = authRemoteDataSource.signup(signupRequest)
        //todo: when you enable code verification, replace the last 2 lines with these comments
//        authLocalDataSource.saveAccountState(false)
//        authLocalDataSource.savePhone(body.phone)

        authLocalDataSource.saveUserData(authDto)
        authLocalDataSource.saveAccountState(true)
    }

    suspend fun login(loginRequest: LoginRequest) {
        val authDto = authRemoteDataSource.login(loginRequest)
        authLocalDataSource.saveUserData(authDto)
    }

    suspend fun verifyCode(verifyCodeRequest: VerifyCodeRequest) {
        val authDto = authRemoteDataSource.verifyCode(verifyCodeRequest)
        authLocalDataSource.saveUserData(authDto)
        authLocalDataSource.saveAccountState(true)
    }


    suspend fun clearUserData() {
        authLocalDataSource.clearUserData()
    }

    suspend fun getStoredAuthData(): AuthDto {
        return authLocalDataSource.getStoredAuthData()
    }

    suspend fun checkIsAuthDtoStored(): Boolean {
        return authLocalDataSource.getStoredAuthData().uuid?.isNotBlank() == true
    }

    suspend fun checkIsAccountActive(): Boolean {
        return authLocalDataSource.getIsAccountActive()
    }

    suspend fun getPhone(): String {
        return authLocalDataSource.getPhone()
    }

}