package com.example.ui.notifications

import com.example.domain.model.Notification

data class NotificationUIState(
    val notifications: List<Notification> = listOf(),
)