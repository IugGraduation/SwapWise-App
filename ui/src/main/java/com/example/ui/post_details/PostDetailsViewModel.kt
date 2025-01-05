package com.example.ui.post_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetPostDetailsUseCase
import com.example.domain.model.State
import com.example.ui.models.PostItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostDetailsUseCase: GetPostDetailsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PostItemUiState())
    val state = _state.asStateFlow()

    val args = PostDetailsArgs(savedStateHandle)


    init {
        viewModelScope.launch {
            getPostDetailsUseCase(args.uuid).collect { state ->
                _state.value = when (state) {
                    is State.Loading -> PostItemUiState(isLoading = true)
                    is State.Success -> PostItemUiState(postItem = state.data)
                    is State.Error -> PostItemUiState(error = state.message)
                }
            }
        }
    }

}