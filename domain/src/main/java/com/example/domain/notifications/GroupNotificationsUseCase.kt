package com.example.domain.notifications

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.domain.model.Notification
import com.example.domain.model.NotificationGroup
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GroupNotificationsUseCase @Inject constructor() {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(notifications: List<Notification>): List<NotificationGroup> {
        return notifications.groupBy { notification ->
            when {
                notification.date.isEqual(LocalDate.now()) -> "Today"
                notification.date.isEqual(LocalDate.now().minusDays(1)) -> "Yesterday"
                else -> notification.date.format(DateTimeFormatter.ofPattern("MMM d"))
            }
        }.map { (title, notifications) ->
            NotificationGroup(title, notifications)
        }
    }
}