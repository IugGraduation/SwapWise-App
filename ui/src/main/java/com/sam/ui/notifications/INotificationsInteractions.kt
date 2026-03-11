package com.sam.ui.notifications

import com.sam.domain.model.NotificationGroup

interface INotificationsInteractions {
    fun onDismiss (id: String)
    fun getGroupedNotifications(): List<NotificationGroup>
    fun refresh()
}