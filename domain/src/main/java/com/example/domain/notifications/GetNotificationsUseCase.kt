package com.example.domain.notifications

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.repository.NotificationsRepository
import com.example.domain.model.Notification
import kotlinx.coroutines.delay
import java.time.LocalDate
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(private val notificationsRepository: NotificationsRepository) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(): List<Notification> {
        delay(500)
        return GetFakeNotificationsUseCase()()
    }
}


class GetFakeNotificationsUseCase {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(): List<Notification> {
        val notification = Notification(
            id = "1", message = "message", date = LocalDate.now()
        )
        val notificationsList = listOf(
            notification,
            notification,
            notification,
            notification,
            notification,
        ).mapIndexed { index, item ->
            item.copy(
                id = index.toString(),
                message = item.message + " " + index,
                date = if (index == 3) LocalDate.now().minusDays(2) else LocalDate.now()
                    .minusDays(index.toLong())
            )
        }
        return notificationsList
    }
}
