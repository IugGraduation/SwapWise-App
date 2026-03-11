package com.sam.ui.notifications

import com.sam.domain.model.Notification
import com.sam.ui.models.AsyncState

data class NotificationUIState(
    val notifications: AsyncState<List<Notification>> = AsyncState.Initial,
)