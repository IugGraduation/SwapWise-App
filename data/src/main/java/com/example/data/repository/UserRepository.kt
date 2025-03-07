package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.data.model.response.profile.ProfileDto
import com.example.data.source.remote.ProfileDataSource
import com.example.data.util.checkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val profileDataSource: ProfileDataSource
){

    suspend fun getCurrentUserById(id: String): ProfileDto? {
        return checkResponse { profileDataSource.getCurrentUserDataById(id) }
    }

    suspend fun updateDarkTheme(isDarkTheme:Boolean){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_THEME] = isDarkTheme
        }
    }

    fun isDarkThemeEnabled(): Flow<Boolean> {
        return dataStore.data.map {
            it[PreferencesKeys.IS_DARK_THEME] ?: false
        }
    }

    private object PreferencesKeys {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }
}