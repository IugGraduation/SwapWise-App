package com.example.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.data.model.response.UserDto
import com.example.data.model.response.profile.ProfileDto
import com.example.data.source.remote.ProfileDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val profileDataSource: ProfileDataSource
){

    suspend fun getCurrentUserById(id: String): ProfileDto? {
        Log.e("bk", "response body: ${profileDataSource.getCurrentUserDataById(id,"37|6w2gbZiIsfjd3uW2I5lOroyKMjPwIqe91alt9N3Y57c827d1").body()}")
        return profileDataSource.getCurrentUserDataById(id, "37|6w2gbZiIsfjd3uW2I5lOroyKMjPwIqe91alt9N3Y57c827d1").body()
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