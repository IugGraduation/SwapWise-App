package com.example.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.model.response.AuthDto
import com.example.data.util.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AuthDataStoreSourceImpl(
    private val dataStore: DataStore<Preferences>
) : AuthLocalDataSource {

    private object PreferencesKeys {
        val image = stringPreferencesKey("image")
        val name = stringPreferencesKey("name")
        val userId = stringPreferencesKey("user_id")
        val token = stringPreferencesKey(Constants.TOKEN)
        val isAccountActive = booleanPreferencesKey("is_account_active")
        val phone = stringPreferencesKey("phone")
    }

    override suspend fun saveUserData(authDto: AuthDto?) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.image] = authDto?.image ?: ""
            preferences[PreferencesKeys.name] = authDto?.name ?: ""
            preferences[PreferencesKeys.userId] = authDto?.uuid ?: ""
            preferences[PreferencesKeys.token] = authDto?.token ?: ""
        }
    }

    override suspend fun saveAccountState(isAccountActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.isAccountActive] = isAccountActive
        }
    }

    override suspend fun savePhone(phone: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.phone] = phone
        }
    }

    override suspend fun getStoredAuthData(): AuthDto {

        return dataStore.data.map { preferences ->
            AuthDto(
                image = preferences[PreferencesKeys.image] ?: "",
                name = preferences[PreferencesKeys.name] ?: "",
                uuid = preferences[PreferencesKeys.userId] ?: "",
                token = preferences[PreferencesKeys.token] ?: "",
            )
        }.first()

    }

    override suspend fun getPhone(): String {
        return dataStore.data.map {
            it[PreferencesKeys.phone] ?: ""
        }.first()
    }

    override suspend fun getIsAccountActive(): Boolean {
        return dataStore.data.map { preferences ->
            (preferences[PreferencesKeys.isAccountActive] == true)
        }.first()
    }

    override suspend fun getToken(): String? {
        return dataStore.data.map {
            (it[PreferencesKeys.token])
        }.first()
    }

    override suspend fun clearUserData() {
        dataStore.edit { it.clear() }
    }

}