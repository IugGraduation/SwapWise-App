package com.example.data.source.local

import com.example.data.model.response.AuthDto

interface AuthLocalDataSource {
    suspend fun saveUserData(authDto: AuthDto?)

    suspend fun saveAccountState(isAccountActive: Boolean)
    suspend fun savePhone(phone: String)

    suspend fun getStoredAuthData(): AuthDto
    suspend fun getPhone(): String
    suspend fun getIsAccountActive(): Boolean
    suspend fun getToken(): String?

    suspend fun clearUserData()
}