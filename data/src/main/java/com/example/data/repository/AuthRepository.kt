package com.example.data.repository

import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.request.VerifyCodeRequest
import com.example.data.model.response.AuthDto
import com.example.data.source.local.AuthLocalDataSource
import com.example.data.source.remote.AuthRemoteDataSource
import com.example.data.util.checkResponse

class AuthRepository(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) {
    suspend fun signup(body: SignupRequest) {
        checkResponse { authRemoteDataSource.signup(body) }
        authLocalDataSource.saveAccountState(false)
        authLocalDataSource.saveMobile(body.mobile)
    }

    suspend fun login(body: LoginRequest) {
        val authDto = checkResponse { authRemoteDataSource.login(body) }
        authLocalDataSource.saveUserData(authDto)
    }

    suspend fun verifyCode(body: VerifyCodeRequest) {
        val authDto = checkResponse { authRemoteDataSource.verifyCode(body) }
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