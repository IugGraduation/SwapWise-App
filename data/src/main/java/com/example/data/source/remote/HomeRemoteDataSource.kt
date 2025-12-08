package com.example.data.source.remote

import com.example.data.model.response.HomeDto
import com.example.data.model.response.PostItemDto

interface HomeRemoteDataSource {

    suspend fun getHomeDto(languageCode: String): HomeDto?

    suspend fun seeAll(languageCode: String, type: String): List<PostItemDto>?

    suspend fun getPostsFromCategory(languageCode: String, categoryId: String): List<PostItemDto>?
}