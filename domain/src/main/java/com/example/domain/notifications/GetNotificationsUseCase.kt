package com.example.domain.notifications

import com.example.data.repository.NotificationsRepository
import com.example.domain.model.Notification
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(private val notificationsRepository: NotificationsRepository) {
    suspend operator fun invoke(): List<Notification> {
        return Notification.fromNotificationDtoList(notificationsRepository.getNotifications())
    }
}


class GetFakeNotificationsUseCase {
    operator fun invoke(): List<Notification> {
        val notification = Notification(
            id = "1", message = "message", date = Date().toString()
        )
        val notificationsList = listOf(
            notification,
            notification,
            notification,
            notification,
            notification,
        ).mapIndexed { index, item ->
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -if (index == 3) 2 else index)
            item.copy(
                id = index.toString(),
                message = item.message + " " + index,
                date = calendar.time.toString()
            )
        }
        return notificationsList
    }
}
