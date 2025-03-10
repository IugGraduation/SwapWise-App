package com.example.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.data.model.response.profile.ProfileDto
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
        Log.e("bk", "${profileDataSource.getCurrentUserDataById(id).body()}")
        return checkResponse { profileDataSource.getCurrentUserDataById(id) }
    }

    suspend fun updateUserInfo(
        name: RequestBody,
        mobile: RequestBody,
        place: RequestBody,
        image: MultipartBody.Part?,
        bio: RequestBody
    ): Boolean {


        Log.e("bk", "from repo: image part${image}")
        val response = profileDataSource.updateUserInfo(
            image = image,
            name = name,
            mobile = mobile,
            bio = bio,
            place = place
        )

        Log.e("bk", "response body: ${response.body()}")
        return response.body()?.status ?: false
    }


//    suspend fun updateUerInfo(
//        image: String,
//        name: String,
//        mobile: String,
//        bio: String,
//        place: String
//    ): Boolean {
//        val response  = profileDataSource.updateUserInfo(
//            UpdateUserInfoRequest(
//                image = image,
//                name = name,
//                mobile = mobile,
//                bio = bio,
//                place = place
//            )
//        )
//        Log.e("bk", " name: $name, response code: ${response.code()}, response body: ${response.body()}, error body: ${response.errorBody()?.string()}")
//        return response.body()?.status ?: false
//    }

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