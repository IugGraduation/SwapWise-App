package com.example.domain.notifications

import com.example.domain.model.Notification
import com.example.domain.model.NotificationGroup
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GroupNotificationsUseCase @Inject constructor() {
    operator fun invoke(notifications: List<Notification>): List<NotificationGroup> {
        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())

        return notifications.groupBy { notification ->
            val notificationDate = Calendar.getInstance().apply { time = notification.date }

            when {
                isSameDay(notificationDate, today) -> "Today"
                isSameDay(notificationDate, yesterday) -> "Yesterday"
                else -> dateFormat.format(notification.date)
            }
        }.map { (title, notifications) ->
            NotificationGroup(title, notifications)
        }
    }

    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

}