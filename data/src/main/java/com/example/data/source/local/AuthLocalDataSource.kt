package com.example.data.source.local

import com.example.data.model.response.AuthDto

interface AuthLocalDataSource {
    suspend fun saveUserData(authDto: AuthDto?)

    suspend fun saveAccountState(isAccountActive: Boolean)
    suspend fun saveMobile(mobile: String)

    suspend fun getStoredAuthData(): AuthDto
    suspend fun getMobile(): String
    suspend fun getIsAccountActive(): Boolean
    suspend fun getToken(): String?

    suspend fun clearUserData()
}