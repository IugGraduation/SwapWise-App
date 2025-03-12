package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.data.model.request.ResetPasswordRequest
import com.example.data.model.response.profile.ProfileDto
import com.example.data.model.response.profile.ProfilePostItemDto
import com.example.data.source.remote.ProfileDataSource
import com.example.data.util.checkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val profileDataSource: ProfileDataSource
) {

    suspend fun getCurrentUserById(id: String): ProfileDto? {
        return checkResponse { profileDataSource.getCurrentUserDataById(id) }
    }

    suspend fun getCurrentUserPosts(): List<ProfilePostItemDto>?{
        return checkResponse { profileDataSource.getCurrentUserPosts() }
    }

    suspend fun updateUserInfo(
        name: RequestBody,
        mobile: RequestBody,
        place: RequestBody,
        image: MultipartBody.Part?,
        bio: RequestBody
    ): Boolean {
        val response = profileDataSource.updateUserInfo(
            image = image,
            name = name,
            mobile = mobile,
            bio = bio,
            place = place
        )

        return response.body()?.status ?: false
    }

    suspend fun resetPassword(
        currentPassword: String,
        newPassword: String,
        confirmNewPassword: String
    ) {
        checkResponse {
            profileDataSource.resetPassword(
                request = ResetPasswordRequest(
                    currentPassword = currentPassword,
                    newPassword = newPassword,
                    confirmNewPassword = confirmNewPassword
                )
            )
        }
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

    private object PreferencesKeys {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }
}