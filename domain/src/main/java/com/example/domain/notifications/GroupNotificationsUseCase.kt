package com.example.domain.notifications

import com.example.domain.model.Notification
import com.example.domain.model.NotificationGroup
import javax.inject.Inject

class GroupNotificationsUseCase @Inject constructor() {
    operator fun invoke(notifications: List<Notification>): List<NotificationGroup> {
        return notifications.groupBy { notification ->
            notification.date
        }.map { (title, notifications) ->
            NotificationGroup(title, notifications)
        }
    }

}