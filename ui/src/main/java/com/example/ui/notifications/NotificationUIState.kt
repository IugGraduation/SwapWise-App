package com.example.ui.notifications

import com.example.domain.model.Notification
import com.example.ui.models.AsyncState

data class NotificationUIState(
    val notifications: AsyncState<List<Notification>> = AsyncState.Initial,
)