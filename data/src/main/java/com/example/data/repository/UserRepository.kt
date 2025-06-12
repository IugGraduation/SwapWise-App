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
import com.example.data.source.remote.ProfileRetrofitDataSource
import com.example.data.util.checkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val profileRetrofitDataSource: ProfileRetrofitDataSource,
    private val profileRemoteDataSource: ProfileRemoteDataSource,
) {

    suspend fun getCurrentUserById(id: String): ProfileDto? {
        return profileRemoteDataSource.getCurrentUserDataById(id)
    }

    suspend fun getCurrentUserPosts(): List<ProfilePostItemDto>?{
        return profileRemoteDataSource.getCurrentUserPosts()
    }

    suspend fun updateUserInfo(
        name: RequestBody,
        mobile: RequestBody,
        place: RequestBody,
        image: MultipartBody.Part?,
        bio: RequestBody
    ): Boolean {
        val response = profileRetrofitDataSource.updateUserInfo(
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
            profileRetrofitDataSource.resetPassword(
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

    suspend fun updateAppLanguage(newLanguage: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOCAL_LANGUAGE] = newLanguage
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