package com.example.data.source.remote

import com.example.data.model.response.NotificationDto

interface NotificationsRemoteDataSource {

    suspend fun getNotifications(): List<NotificationDto>

}