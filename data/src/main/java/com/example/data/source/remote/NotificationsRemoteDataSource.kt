package com.example.data.source.remote

import com.example.data.model.response.ApiResponseDto
import com.example.data.model.response.NotificationDto
import retrofit2.Response
import retrofit2.http.GET

interface NotificationsRemoteDataSource {

    @GET("notifications")
    suspend fun getNotifications(
    ): Response<ApiResponseDto<List<NotificationDto>>>

}