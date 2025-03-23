package com.example.domain.model

import com.example.data.model.response.NotificationDto

data class Notification(
    val id: String = "",
    val userImage: String = "",
    val message: String = "",
    val date: String = "",
) {
    companion object {
        private fun fromNotificationDto(notificationDto: NotificationDto): Notification {
            return Notification(
                id = notificationDto.uuid ?: "",
                userImage = notificationDto.icon ?: "",
                message = notificationDto.content ?: "",
                date = notificationDto.createdAt ?: ""
            )
        }

        fun fromNotificationDtoList(notificationDtoList: List<NotificationDto?>?): List<Notification> {
            return notificationDtoList?.filterNotNull()?.map {
                fromNotificationDto(it)
            } ?: listOf()
        }
    }
}


data class NotificationGroup(
    val title: String = "",
    val notifications: List<Notification> = listOf()
)

