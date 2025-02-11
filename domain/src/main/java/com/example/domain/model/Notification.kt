package com.example.domain.model

import java.util.Date

data class Notification(
    val id: String = "",
    val userImage: String = "",
    val message: String = "",
    val date: Date = Date(),
)


data class NotificationGroup(
    val title: String = "",
    val notifications: List<Notification> = listOf()
)

