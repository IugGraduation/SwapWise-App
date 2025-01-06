package com.example.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.model.NotificationDto
import com.example.data.model.StateDto
import com.example.data.util.fakeWrapWithFlow
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class NotificationsRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getNotifications(): Flow<StateDto<List<NotificationDto>>> {
        val notification = NotificationDto(
            id = "1", title = "hi", message = "message", date = LocalDate.now()
        )

        return fakeWrapWithFlow(listOf(notification,))
    }
}