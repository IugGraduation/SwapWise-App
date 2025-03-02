package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.exception.DataException
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.request.VerifyCodeRequest
import com.example.data.model.response.AuthDto
import com.example.data.source.remote.AuthRemoteDataSource
import com.example.data.util.checkResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val dataStore: DataStore<Preferences>,
) {
    suspend fun signup(body: SignupRequest) {
        checkResponse { authRemoteDataSource.signup(body) }
        saveAccountState(false)
        saveMobile(body.mobile)
    }

    suspend fun login(body: LoginRequest) {
        val authDto = checkResponse { authRemoteDataSource.login(body) }?.data
        saveUserDataToDataStore(authDto)
    }

    suspend fun verifyCode(body: VerifyCodeRequest) {
        val authDto = checkResponse { authRemoteDataSource.verifyCode(body) }?.data
        saveUserDataToDataStore(authDto)
        saveAccountState(true)
    }

    private suspend fun saveUserDataToDataStore(authDto: AuthDto?) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.image] = authDto?.image ?: ""
            preferences[PreferencesKeys.name] = authDto?.name ?: ""
            preferences[PreferencesKeys.userId] = authDto?.uuid ?: ""
            preferences[PreferencesKeys.token] = authDto?.token ?: ""
        }
    }

    private suspend fun saveAccountState(isAccountActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.isAccountActive] = isAccountActive
        }
    }

    private suspend fun saveMobile(mobile: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.mobile] = mobile
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

    suspend fun checkAuthDto() {
        dataStore.data.map {
            if(it[PreferencesKeys.token].isNullOrBlank()){
            if (it[PreferencesKeys.isAccountActive] == false) throw DataException.InactiveAccountException()
            throw DataException.EmptyDataException()
            }
        }.first()
    }

    suspend fun getMobile(): String {
        return dataStore.data.map {
            it[PreferencesKeys.mobile] ?: ""
        }.first()
    }

    private object PreferencesKeys {
        val image = stringPreferencesKey("image")
        val name = stringPreferencesKey("name")
        val userId = stringPreferencesKey("user_id")
        val token = stringPreferencesKey("token")
        val isAccountActive = booleanPreferencesKey("is_account_active")
        val mobile = stringPreferencesKey("mobile")
    }
}