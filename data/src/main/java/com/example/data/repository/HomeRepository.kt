package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.model.response.PostItemDto
import com.example.data.source.remote.HomeRemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class HomeRepository(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private val dataStore: DataStore<Preferences>,
) {
    suspend fun getHomeDto() = homeRemoteDataSource.getHomeDto()

    suspend fun seeAll(url: String) = homeRemoteDataSource.seeAll(url)

    suspend fun getPostsFromCategory(categoryId: String) =
        homeRemoteDataSource.getPostsFromCategory(categoryId)

    suspend fun getCategories(): List<PostItemDto>? {
        if (checkIsCategoriesStored()) {
            return getCategoriesFromDataStore()
        } else {
            val categories = homeRemoteDataSource.seeAll("Categories")
            saveCategoriesToDataStore(categories ?: emptyList())
            return categories
        }
    }


    private suspend fun checkIsCategoriesStored(): Boolean {
        return dataStore.data.map {
            !it[PreferencesKeys.topics].isNullOrEmpty()
        }.first()
    }

    private suspend fun saveCategoriesToDataStore(topics: List<PostItemDto>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.topics] = Json.encodeToString(topics)
        }
    }

    private suspend fun getCategoriesFromDataStore(): List<PostItemDto> {
        return dataStore.data.map { preferences ->
            try {
                Json.decodeFromString<List<PostItemDto>>(
                    preferences[PreferencesKeys.topics] ?: ""
                )
            } catch (e: Exception) {
                emptyList()
            }
        }.first()
    }

    private object PreferencesKeys {
        val topics = stringPreferencesKey("topics")
    }
}
