package com.example.ui.post_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.GetPostDetailsUseCase
import com.example.domain.model.PostItem
import com.example.ui.base.BaseUiState
import com.example.ui.base.BaseViewModel
import com.example.ui.models.PostItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostDetailsUseCase: GetPostDetailsUseCase
) : BaseViewModel<PostItemUiState>(PostItemUiState()) {
    val args = PostDetailsArgs(savedStateHandle)


    init {
        viewModelScope.launch {
            getPostDetails()
        }
    }

    private fun getPostDetails() {
        onActionLoading()
        tryToExecute(
            call = { getPostDetailsUseCase(args.postId) },
            onSuccess = ::onGetOfferDetailsSuccess,
            onError = ::onActionFail
        )
    }

    private fun onActionLoading() {
        updateBaseUiState { copy(isLoading = true) }
    }

    private fun onGetOfferDetailsSuccess(data: PostItem) {
        _state.value = PostItemUiState(postItem = data)
    }

    private fun onActionFail(throwable: Throwable) {
        updateBaseUiState { copy(isLoading = false, errorMessage = throwable.message ?: "") }
    }

    private fun updateBaseUiState(update: BaseUiState.() -> BaseUiState) {
        _state.update {
            it.copy(baseUiState = it.baseUiState.update())
        }
    }

}