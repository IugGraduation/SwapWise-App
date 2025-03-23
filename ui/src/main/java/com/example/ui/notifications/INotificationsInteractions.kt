package com.example.ui.notifications

import com.example.domain.model.NotificationGroup

interface INotificationsInteractions {
    fun onDismiss (id: String)
    fun getGroupedNotifications(): List<NotificationGroup>
}