package com.example.ui.notifications

import com.example.domain.model.Notification
import com.example.ui.base.BaseUiState

data class NotificationUIState(
    val notifications: List<Notification> = listOf(),
    val baseUiState: BaseUiState = BaseUiState()
)