package com.example.data.source.remote

import com.example.data.model.response.ApiResponseDto
import com.example.data.model.response.TopicItemDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SearchRemoteDataSource {

    @GET("search")
    suspend fun search(
        @Query("search") search: String,
        @QueryMap categoriesIds: Map<String, String>?
    ): Response<ApiResponseDto<List<TopicItemDto>>>

}