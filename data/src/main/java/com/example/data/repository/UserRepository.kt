package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.model.request.ResetPasswordRequest
import com.example.data.model.response.profile.ProfileDto
import com.example.data.model.response.profile.ProfilePostItemDto
import com.example.data.repository.UserRepository.PreferencesKeys.LOCAL_LANGUAGE
import com.example.data.source.remote.ProfileRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val profileRemoteDataSource: ProfileRemoteDataSource,
) {

    suspend fun getCurrentUserById(id: String): ProfileDto? {
        return profileRemoteDataSource.getCurrentUserDataById(id)
    }

    suspend fun getCurrentUserPosts(): List<ProfilePostItemDto>?{
        return profileRemoteDataSource.getCurrentUserPosts()
    }

    suspend fun updateUserInfo(
        name: String,
        mobile: String,
        place: String,
        imageByteArray: ByteArray?,
        bio: String
    ): Boolean {
        return profileRemoteDataSource.updateUserInfo(
            name = name,
            mobile = mobile,
            place = place,
            imageByteArray = imageByteArray,
            bio = bio,
        )
    }

    suspend fun resetPassword(
        currentPassword: String,
        newPassword: String,
        confirmNewPassword: String
    ) {
        profileRemoteDataSource.resetPassword(
            request = ResetPasswordRequest(
                currentPassword = currentPassword,
                newPassword = newPassword,
                confirmNewPassword = confirmNewPassword
            )
        )
    }

    suspend fun updateDarkTheme(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_THEME] = isDarkTheme
        }
    }

    fun isDarkThemeEnabled(): Flow<Boolean> {
        return dataStore.data.map {
            it[PreferencesKeys.IS_DARK_THEME] ?: false
        }
    }

    suspend fun updateAppLanguage(newLanguage: String) {
        dataStore.edit { preferences ->
            preferences[LOCAL_LANGUAGE] = newLanguage
        }
    }

    fun getLatestSelectedAppLanguage(): Flow<String> {
        return dataStore.data.map {
            it[LOCAL_LANGUAGE] ?: PreferencesKeys.ENGLISH
        }
    }
    private object PreferencesKeys {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        val LOCAL_LANGUAGE = stringPreferencesKey("LOCAL_LANGUAGE")
         const val ENGLISH = "en"
    }
}