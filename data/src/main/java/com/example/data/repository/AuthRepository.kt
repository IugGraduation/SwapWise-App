package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.response.AuthDto
import com.example.data.source.remote.AuthenticationRemoteDataSource
import com.example.data.util.checkResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource,
    private val dataStore: DataStore<Preferences>,
) {
    suspend fun signup(body: SignupRequest) {
        val authDto = checkResponse { authenticationRemoteDataSource.signup(body) }?.data
        saveUserDataToDataStore(authDto)
    }

    suspend fun login(body: LoginRequest) {
        val authDto = checkResponse { authenticationRemoteDataSource.login(body) }?.data
        saveUserDataToDataStore(authDto)

    }

    private suspend fun saveUserDataToDataStore(authDto: AuthDto?) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.image] = authDto?.image ?: ""
            preferences[PreferencesKeys.name] = authDto?.name ?: ""
            preferences[PreferencesKeys.userId] = authDto?.uuid ?: ""
            preferences[PreferencesKeys.token] = authDto?.token ?: ""
        }
    }


    suspend fun getAuthDto(): AuthDto {
        return dataStore.data.map {
            AuthDto(
                image = it[PreferencesKeys.image] ?: "",
                name = it[PreferencesKeys.name] ?: "",
                token = it[PreferencesKeys.token] ?: "",
                uuid = it[PreferencesKeys.userId] ?: "",
            )
        }.first()
    }


    private object PreferencesKeys {
        val image = stringPreferencesKey("image")
        val name = stringPreferencesKey("name")
        val userId = stringPreferencesKey("user_id")
        val token = stringPreferencesKey("token")
    }
}