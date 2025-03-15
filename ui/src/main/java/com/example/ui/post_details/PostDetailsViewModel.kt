package com.example.ui.post_details

import androidx.lifecycle.SavedStateHandle
import com.example.domain.authentication.GetAuthUseCase
import com.example.domain.model.OfferItem
import com.example.domain.model.PostItem
import com.example.domain.post.GetPostDetailsUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.MyUiState
import com.example.ui.models.PostItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostDetailsUseCase: GetPostDetailsUseCase,
    private val getAuthUseCase: GetAuthUseCase
) : BaseViewModel<PostItemUiState, PostDetailsEffects>(PostItemUiState()), PostDetailsInteractions {
    private val args = PostDetailsArgs(savedStateHandle)

    fun onResume() {
        getPostDetails()
    }

    private fun getPostDetails() {
        tryToExecute(
            call = { getPostDetailsUseCase(args.postId) },
            onSuccess = ::onGetPostDetailsSuccess,
            shouldLoad = _state.value.data.postItem.uuid.isBlank(),
            shouldHideContent = true
        )
    }

    private fun onGetPostDetailsSuccess(data: PostItem) {
        _state.value = MyUiState(PostItemUiState(postItem = data))
    }


    override fun navigateToAddOffer() {
        sendUiEffect(PostDetailsEffects.NavigateToAddOffer)
    }

    override fun navigateToOfferDetails(offerItem: OfferItem) {
        tryToExecute(
            call = { if (offerItem.user.uuid != getAuthUseCase().userId) throw Exception() },
            onSuccess = { sendUiEffect(PostDetailsEffects.NavigateToEditOffer(offerItem.uuid)) },
            onError = { sendUiEffect(PostDetailsEffects.NavigateToOfferDetails(offerItem.uuid)) },
        )
    }

    override fun navigateUp() {
        sendUiEffect(PostDetailsEffects.NavigateUp)
    }

}