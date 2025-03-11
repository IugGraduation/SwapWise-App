package com.example.data.source.remote

import com.example.data.model.response.ApiResponseDto
import com.example.data.model.response.PostItemDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRemoteDataSource {

    @GET("search")
    suspend fun search(
        @Query("search") search: String,
        @Query("categories_uuid") categoriesIds: List<String>?,
    ): Response<ApiResponseDto<List<PostItemDto>>>

}