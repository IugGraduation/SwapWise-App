package com.example.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class BaseViewModel<STATE, EFFECT>(initialState: STATE) : ViewModel() {

    interface BaseUiEffect

    protected val _state: MutableStateFlow<MyUiState<STATE>> by lazy {
        MutableStateFlow(MyUiState(initialState))
    }
    val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<EFFECT>()
    val effect: SharedFlow<EFFECT> = _effect

    fun sendUiEffect(effect: EFFECT) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }


    fun <T> tryToExecute(
        call: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = ::onActionFail,
        shouldLoad: Boolean = true,
        shouldHideContent: Boolean = false,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        isActionLoading(shouldLoad, shouldHideContent)
        viewModelScope.launch(dispatcher) {
            try {
                call().also(onSuccess)
            } catch (throwable: Throwable) {
                onError(throwable)
            } finally {
                isActionLoading(isLoading = false, shouldHideContent = false)
            }
        }
    }

    fun tryToExecute(
        call: suspend () -> Unit,
        onSuccess: () -> Unit = {},
        onError: (Throwable) -> Unit = ::onActionFail,
        shouldLoad: Boolean = true,
        shouldHideContent: Boolean = false,
        isLoadableAction: Boolean = true,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        if (isLoadableAction) isActionLoading(shouldLoad, shouldHideContent)
        viewModelScope.launch(dispatcher) {
            try {
                call()
                onSuccess()
            } catch (throwable: Throwable) {
                onError(throwable)
            } finally {
                if (isLoadableAction) isActionLoading(isLoading = false, shouldHideContent = false)
            }
        }
    }

    protected fun updateData(update: STATE.() -> STATE) {
        _state.update {
            it.copy(data = it.data.update())
        }
    }


    private fun updateBaseUiState(update: BaseUiState.() -> BaseUiState) {
        _state.update {
            it.copy(baseUiState = it.baseUiState.update())
        }
    }

    protected fun isActionLoading(isLoading: Boolean = true, shouldHideContent: Boolean = false) {
        updateBaseUiState { copy(isLoading = isLoading, shouldHideContent = shouldHideContent) }
    }

    protected fun onActionFail(throwable: Throwable) {
        updateErrorMessage(throwable.message.orEmpty())
    }

    protected fun updateErrorMessage(errorMessage: String = "") {
        viewModelScope.launch {
            _state.value.baseUiState.errorSharedFlow.emit(errorMessage)
        }
    }
}