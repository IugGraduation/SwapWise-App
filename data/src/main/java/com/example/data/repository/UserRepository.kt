package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
){

    suspend fun updateDarkTheme(isDarkTheme:Boolean){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_THEME] = isDarkTheme
        }
    }

    suspend fun isDarkThemeEnabled(): Flow<Boolean> {
        return dataStore.data.map {
            it[PreferencesKeys.IS_DARK_THEME] ?: false
        }
    }


    private object PreferencesKeys {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }
}