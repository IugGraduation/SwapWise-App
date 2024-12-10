package com.example.ui.post_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.domain.GetPostsUseCase
import com.example.domain.models.PostItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _state = MutableStateFlow(PostItem())
    val state = _state.asStateFlow()

    val args = PostDetailsArgs(savedStateHandle)


    init {
        _state.value = GetPostsUseCase()()[0]
    }

}