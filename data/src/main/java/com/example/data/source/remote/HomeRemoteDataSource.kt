package com.example.data.source.remote

import com.example.data.model.response.ApiResponseDto
import com.example.data.model.response.HomeDto
import retrofit2.Response
import retrofit2.http.GET

interface HomeRemoteDataSource {

    @GET("home")
    suspend fun getHomeDto(): Response<ApiResponseDto<HomeDto>>

}