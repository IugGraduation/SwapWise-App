package com.example.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.exception.EmptyDataException
import com.example.domain.exception.NetworkException
import com.example.domain.exception.ServerException
import com.example.domain.exception.SwapWiseException
import com.example.domain.exception.UnAuthorizedException
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
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                call().also(onSuccess)
            } catch (e: ServerException) {
                onError(e)
            } catch (e: NetworkException) {
                onError(e)
            } catch (e: UnAuthorizedException) {
                onError(e)
            } catch (e: EmptyDataException) {
                onError(e)
            } catch (e: SwapWiseException) {
                onError(e)
            } catch (throwable: Throwable) {
                onError(throwable)
            }
        }
    }

}