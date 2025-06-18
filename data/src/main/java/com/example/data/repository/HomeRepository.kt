package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.model.response.TopicItemDto
import com.example.data.source.remote.HomeRemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class HomeRepository(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private val dataStore: DataStore<Preferences>,
) {
    suspend fun getHomeDto() = homeRemoteDataSource.getHomeDto()

    suspend fun seeAll(url: String) = homeRemoteDataSource.seeAll(url)

    suspend fun getPostsFromCategory(categoryId: String) =
        homeRemoteDataSource.getPostsFromCategory(categoryId)

    suspend fun getCategories(): List<TopicItemDto>? {
        if (checkIsCategoriesStored()) {
            return getCategoriesFromDataStore()
        } else {
            val categories = homeRemoteDataSource.seeAll("category")
            saveCategoriesToDataStore(categories ?: emptyList())
            return categories
        }
    }


    private suspend fun checkIsCategoriesStored(): Boolean {
        return dataStore.data.map {
            !it[PreferencesKeys.topics].isNullOrEmpty()
        }.first()
    }

    private suspend fun saveCategoriesToDataStore(topics: List<TopicItemDto>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.topics] = TODO()
            //   Gson().toJson(topics, object : TypeToken<List<TopicItemDto>>() {}.type)
        }
    }

    suspend fun getCategoriesFromDataStore(): List<TopicItemDto> {
//        return dataStore.data.map { preferences ->
//            try {
//                Gson().fromJson<List<TopicItemDto>>(
//                    preferences[PreferencesKeys.topics] ?: "",
//                    object : TypeToken<List<TopicItemDto>>() {}.type
//                ) ?: emptyList()
//            } catch (e: Exception) {
//                emptyList()
//            }
//        }.first()
        TODO()
    }

    private object PreferencesKeys {
        val topics = stringPreferencesKey("topics")
    }
}

