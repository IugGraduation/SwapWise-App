package com.example.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel<STATE>(initialState: STATE) : ViewModel() {

    protected val _state: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val state = _state.asStateFlow()

    fun <T> tryToExecute(
        call: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = ::onActionFail,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            isActionLoading(true)
            try {
                call().also(onSuccess)
                isActionLoading(false)
            } catch (throwable: Throwable) {
                isActionLoading(false)
                onError(throwable)
            }
        }
    }


    private fun updateBaseUiState(update: BaseUiState.() -> BaseUiState) {
//        _state.update {
//            it.copy(baseUiState = it.baseUiState.update())
//        }
    }

    protected fun isActionLoading(isLoading: Boolean = true) {
        updateBaseUiState { copy(isLoading = isLoading) }
    }

    protected fun onActionFail(throwable: Throwable) {
        updateBaseUiState { copy(errorMessage = throwable.message ?: "") }
    }

    protected fun onActionFail(errorMessage: String = "") {
        updateBaseUiState { copy(errorMessage = errorMessage) }
    }

    protected fun navigateUp() {
        updateBaseUiState { copy(shouldNavigateUp = true) }
    }

}