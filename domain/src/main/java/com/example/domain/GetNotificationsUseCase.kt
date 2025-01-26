package com.example.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.model.StateDto
import com.example.data.util.fakeWrapWithFlow
import com.example.domain.model.CategoryItem
import com.example.domain.model.Notification
import com.example.domain.model.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor() {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(): Flow<State<List<Notification>>> {
        return flow {
            emit(State.Loading)
            delay(1000)
            emit(State.Success(GetFakeNotificationsUseCase()()))
        }
    }
}


class GetFakeNotificationsUseCase{
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(): List<Notification> {
        val notification = Notification(
            id = "1",
            message = "message",
            date = LocalDate.now()
        )
        val notificationsList = listOf(
            notification,
            notification,
            notification,
            notification,
            notification,
        ).mapIndexed { index, item -> item.copy(
            id = index.toString(),
            message = item.message + " " + index,
            date = if(index == 3) LocalDate.now().minusDays(2) else LocalDate.now().minusDays(index.toLong())
        ) }

        return notificationsList

    }
}
