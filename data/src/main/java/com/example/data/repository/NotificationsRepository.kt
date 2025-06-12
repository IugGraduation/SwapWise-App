package com.example.data.repository

import com.example.data.source.remote.NotificationsRemoteDataSource
import javax.inject.Inject

class NotificationsRepository @Inject constructor(
    private val notificationsRemoteDataSource: NotificationsRemoteDataSource
) {
    suspend fun getNotifications() = notificationsRemoteDataSource.getNotifications()
}