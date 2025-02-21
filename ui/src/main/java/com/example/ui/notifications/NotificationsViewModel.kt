package com.example.ui.notifications

import com.example.domain.model.Notification
import com.example.domain.model.NotificationGroup
import com.example.domain.notifications.GetNotificationsUseCase
import com.example.domain.notifications.GroupNotificationsUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.MyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val groupNotificationsUseCase: GroupNotificationsUseCase
) : BaseViewModel<NotificationUIState>(NotificationUIState()), INotificationsInteractions {

    init {
        getNotifications()
    }

    private fun getNotifications() {
        tryToExecute(
            call = { getNotificationsUseCase() },
            onSuccess = ::onGetNotificationsSuccess,
            )
    }

    private fun onGetNotificationsSuccess(data: List<Notification>) {
        _state.value = MyUiState(NotificationUIState(notifications = data))
    }


    override fun onDismiss(id: String) {
        updateData {
            copy(notifications = notifications.filterNot { it.id == id })
        }
        //todo: tell api/use-case to delete the notification
    }


    override fun getGroupedNotifications(): List<NotificationGroup> {
        return groupNotificationsUseCase(_state.value.data.notifications)
    }


}