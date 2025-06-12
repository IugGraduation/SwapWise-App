package com.example.domain.model

import com.example.data.model.response.NotificationDto

data class Notification(
    val id: String = "",
    val userImage: String = "",
    val message: String = "",
    val date: String = "",
)

fun NotificationDto.toNotification(): Notification {
    return Notification(
        id = uuid.orEmpty(),
        userImage = icon.orEmpty(),
        message = content.orEmpty(),
        date = createdAt.orEmpty()
    )
}

fun List<NotificationDto?>?.toNotificationList(): List<Notification> {
    return this?.filterNotNull()?.map {
        it.toNotification()
    } ?: listOf()
}


data class NotificationGroup(
    val title: String = "",
    val notifications: List<Notification> = listOf()
)

