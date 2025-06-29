package com.example.data.source.remote

import com.example.data.model.response.HomeDto
import com.example.data.model.response.PostItemDto

interface HomeRemoteDataSource {

    suspend fun getHomeDto(): HomeDto?

    suspend fun seeAll(type: String): List<PostItemDto>?

    suspend fun getPostsFromCategory(categoryId: String): List<PostItemDto>?
}