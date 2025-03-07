package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.util.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    suspend fun getToken(): String {
        val tokenKey = stringPreferencesKey(Constants.TOKEN)
        return dataStore.data.map { preferences ->
            preferences[tokenKey] ?: ""
        }.first()
    }
}