package com.sam.ui.notifications

import com.sam.domain.model.NotificationGroup
import com.sam.domain.notifications.GetNotificationsUseCase
import com.sam.domain.notifications.GroupNotificationsUseCase
import com.sam.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val groupNotificationsUseCase: GroupNotificationsUseCase
) : BaseViewModel<NotificationUIState, Nothing>(NotificationUIState()), INotificationsInteractions {

    init {
        getNotifications()
    }

    private fun getNotifications() {
        tryToExecuteAsync(
            call = { getNotificationsUseCase() },
            stateUpdater = { newState ->
                updateData { copy(notifications = newState) }
            }
        )
    }

    override fun onDismiss(id: String) {
        updateData {
            copy(notifications = notifications.mapData { list ->
                list.filterNot { it.id == id }
            })
        }
        //todo: tell api/use-case to delete the notification
    }


    override fun getGroupedNotifications(): List<NotificationGroup> {
        val currentNotifications = _state.value.data.notifications.data ?: emptyList()
        return groupNotificationsUseCase(currentNotifications)
    }

    override fun refresh() {
        getNotifications()
    }
}