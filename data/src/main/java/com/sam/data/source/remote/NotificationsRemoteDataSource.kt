package com.sam.data.source.remote

import com.sam.data.model.response.NotificationDto

interface NotificationsRemoteDataSource {

    suspend fun getNotifications(): List<NotificationDto>

}