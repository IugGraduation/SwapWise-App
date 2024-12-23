package com.example.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetHomeDataUseCase
import com.example.domain.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(getHomeDataUseCase: GetHomeDataUseCase) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
       viewModelScope.launch {
           getHomeDataUseCase().collect { state ->
               _state.value = when (state) {
                   is State.Loading -> HomeUiState(isLoading = true)
                   is State.Success -> HomeUiState.fromHome(state.data)
                   is State.Error -> HomeUiState(error = state.message)
               }
           }
       }
    }

    fun onNewPostFieldChange(newValue: String) {
        _state.update { it.copy(newPost = newValue) }
    }

}