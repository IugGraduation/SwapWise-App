package com.example.data.source.remote

import com.example.data.model.response.ApiResponseDto
import com.example.data.model.response.HomeDto
import com.example.data.model.response.TopicItemDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeRemoteDataSource {

    @GET("home")
    suspend fun getHomeDto(): Response<ApiResponseDto<HomeDto>>

    @GET("see_all")
    suspend fun seeAll(@Query("type") type: String): Response<ApiResponseDto<List<TopicItemDto>>>
}