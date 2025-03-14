package com.example.data.repository

import com.example.data.source.remote.NotificationsRemoteDataSource
import com.example.data.util.checkResponse
import javax.inject.Inject

class NotificationsRepository @Inject constructor(private val notificationsRemoteDataSource: NotificationsRemoteDataSource) {
    suspend fun getNotifications() =
        checkResponse { notificationsRemoteDataSource.getNotifications() }
}