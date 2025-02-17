package com.example.domain.notifications

import com.example.data.repository.NotificationsRepository
import com.example.domain.model.Notification
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(private val notificationsRepository: NotificationsRepository) {
    suspend operator fun invoke(): List<Notification> {
        delay(500)
        return GetFakeNotificationsUseCase()()
    }
}


class GetFakeNotificationsUseCase {
    operator fun invoke(): List<Notification> {
        val notification = Notification(
            id = "1", message = "message", date = Date()
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
                date = calendar.time
            )
        }
        return notificationsList
    }
}
