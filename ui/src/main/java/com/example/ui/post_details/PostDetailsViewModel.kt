package com.example.ui.post_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetPostDetailsUseCase
import com.example.domain.GetPostsUseCase
import com.example.domain.model.PostItem
import com.example.domain.model.State
import com.example.ui.home.HomeUiState
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
    private val _state = MutableStateFlow(PostItem())
    val state = _state.asStateFlow()

    val args = PostDetailsArgs(savedStateHandle)


    init {
        viewModelScope.launch {
            getPostDetailsUseCase(args.uuid).collect { state ->
                _state.value = when (state) {
                    is State.Loading -> PostItem(isLoading = true)
                    is State.Success -> state.data
                    is State.Error -> PostItem(error = state.message)
                }
            }
        }
    }

}