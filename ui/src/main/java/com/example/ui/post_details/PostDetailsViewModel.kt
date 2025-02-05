package com.example.ui.post_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.GetPostDetailsUseCase
import com.example.domain.model.PostItem
import com.example.ui.base.BaseViewModel
import com.example.ui.models.PostItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostDetailsUseCase: GetPostDetailsUseCase
) : BaseViewModel<PostItemUiState>(PostItemUiState()) {
    private val args = PostDetailsArgs(savedStateHandle)


    init {
        viewModelScope.launch {
            getPostDetails()
        }
    }

    private fun getPostDetails() {
        tryToExecute(
            call = { getPostDetailsUseCase(args.postId) },
            onSuccess = ::onGetOfferDetailsSuccess,
        )
    }

    private fun onGetOfferDetailsSuccess(data: PostItem) {
        _state.value = PostItemUiState(postItem = data)
    }


}