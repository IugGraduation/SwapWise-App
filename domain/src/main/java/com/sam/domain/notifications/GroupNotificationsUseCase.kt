package com.sam.domain.notifications

import com.sam.domain.model.Notification
import com.sam.domain.model.NotificationGroup
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