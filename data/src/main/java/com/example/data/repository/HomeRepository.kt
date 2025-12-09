package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.model.response.PostItemDto
import com.example.data.source.remote.HomeRemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class HomeRepository(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private val dataStore: DataStore<Preferences>,
    private val userRepository: UserRepository
) {
    suspend fun getHomeDto() = homeRemoteDataSource.getHomeDto(getLatestSelectedAppLanguage())

    private suspend fun getLatestSelectedAppLanguage() =
        userRepository.getLatestSelectedAppLanguage().first()

    suspend fun seeAll(url: String) =
        homeRemoteDataSource.seeAll(getLatestSelectedAppLanguage(), url)

    suspend fun getPostsFromCategory(categoryId: String) =
        homeRemoteDataSource.getPostsFromCategory(getLatestSelectedAppLanguage(), categoryId)

    suspend fun getCategories(): List<PostItemDto>? {
        if (checkIsCategoriesStored()) {
            return getCategoriesFromDataStore()
        } else {
            val lang = getLatestSelectedAppLanguage()
            val categories = homeRemoteDataSource.seeAll(lang, "Categories")
            saveCategoriesToDataStore(categories ?: emptyList())
            return categories
        }
    }

    suspend fun clearCategoriesCache() {
        dataStore.edit {
            it.remove(PreferencesKeys.categories)
        }
    }

    private suspend fun checkIsCategoriesStored(): Boolean {
        return dataStore.data.map {
            !it[PreferencesKeys.categories].isNullOrEmpty()
        }.first()
    }

    private suspend fun saveCategoriesToDataStore(topics: List<PostItemDto>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.categories] = Json.encodeToString(topics)
        }
    }

    private suspend fun getCategoriesFromDataStore(): List<PostItemDto> {
        return dataStore.data.map { preferences ->
            try {
                Json.decodeFromString<List<PostItemDto>>(
                    preferences[PreferencesKeys.categories] ?: ""
                )
            } catch (e: Exception) {
                emptyList()
            }
        }.first()
    }

    private object PreferencesKeys {
        val categories = stringPreferencesKey("categories")
    }
}