package com.sam.data.source.remote

import com.sam.data.model.response.HomeDto
import com.sam.data.model.response.PostItemDto

interface HomeRemoteDataSource {

    suspend fun getHomeDto(languageCode: String): HomeDto?

    suspend fun seeAll(languageCode: String, type: String): List<PostItemDto>?

    suspend fun getPostsFromCategory(languageCode: String, categoryId: String): List<PostItemDto>?
}