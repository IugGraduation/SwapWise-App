package com.example.ui.notifications

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetNotificationsUseCase
import com.example.domain.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NotificationsViewModel @Inject constructor(private val getNotificationsUseCase: GetNotificationsUseCase) : ViewModel(), INotificationsInteractions {
    private val _state = MutableStateFlow(NotificationUIState())
    val state = _state.asStateFlow()

    init {
        getNotifications()
    }

    private fun getNotifications(){
        viewModelScope.launch {
            getNotificationsUseCase().collect { state ->
                _state.value = when (state) {
                    is State.Loading -> NotificationUIState(isLoading = true)
                    is State.Success -> NotificationUIState(notifications = state.data)
                    is State.Error -> NotificationUIState(error = state.message)
                }
            }
        }
    }


    override fun onDismiss(id: String){
        _state.update{ it.copy(notifications = it.notifications.filterNot { it.id == id })}
        //todo: tell api/use-case to delete the notification
    }


}