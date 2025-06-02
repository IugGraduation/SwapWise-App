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
    suspend fun signup(body: SignupRequest) {
        val authDto = authRemoteDataSource.signup(body)
        //todo: when you enable code verification, use replace the last 2 lines with these comments
//        authLocalDataSource.saveAccountState(false)
//        authLocalDataSource.saveMobile(body.mobile)

        authLocalDataSource.saveUserData(authDto)
        authLocalDataSource.saveAccountState(true)
    }

    suspend fun login(body: LoginRequest) {
        val authDto = authRemoteDataSource.login(body)
        authLocalDataSource.saveUserData(authDto)
    }

    suspend fun verifyCode(body: VerifyCodeRequest) {
        val authDto = authRemoteDataSource.verifyCode(body)
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
        return authLocalDataSource.getToken()?.isNotBlank() == true
    }

    suspend fun checkIsAccountActive(): Boolean {
        return authLocalDataSource.getIsAccountActive()
    }

    suspend fun getMobile(): String {
        return authLocalDataSource.getMobile()
    }

}