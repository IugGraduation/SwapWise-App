package com.example.ui.post_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.authentication.GetAuthUseCase
import com.example.domain.model.PostItem
import com.example.domain.post.GetPostDetailsUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.models.AsyncState
import com.example.ui.models.ChipUiState
import com.example.ui.models.PostItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostDetailsUseCase: GetPostDetailsUseCase,
    private val getAuthUseCase: GetAuthUseCase
) : BaseViewModel<PostItemUiState, PostDetailsEffects>(PostItemUiState()), PostDetailsInteractions {
    private val args = PostDetailsArgs(savedStateHandle)

    init {
        getPostDetails()
    }


    fun onResume() {
        getPostDetails()
    }

    private fun getPostDetails() {
        tryToExecuteAsync(
            call = { getPostDetailsUseCase(args.postId) },
            stateUpdater = { newState ->
                updateData { copy(postItem = newState) }
                if (newState is AsyncState.Success) {
                    onGetPostDetailsSuccess(newState.data)
                }
            }
        )
    }

    private fun onGetPostDetailsSuccess(data: PostItem) {

        val categoryChips = listOf(
            ChipUiState(
                categoryItem = data.categoryItem,
                selected = mutableStateOf(true),
                clickable = false
            )
        )

        val favoriteChips = data.favoriteCategoryItems.map { category ->
            ChipUiState(
                categoryItem = category,
                selected = mutableStateOf(true),
                clickable = false
            )
        }

        updateData {
            copy(
                categories = AsyncState.Success(categoryChips),
                favoriteCategories = AsyncState.Success(favoriteChips)
            )
        }
        showEditButtonIfNeeded(data)
    }

    private fun showEditButtonIfNeeded(postItem: PostItem) {
        viewModelScope.launch {
            val currentUserId = getAuthUseCase().userId
            val postOwnerId = postItem.user.id
            if (currentUserId.isNotBlank() && currentUserId == postOwnerId) {
                updateData { copy(showEditPostButton = true) }
            }
        }
    }


    override fun navigateToEditPost(postId: String) {
        sendUiEffect(PostDetailsEffects.NavigateToEditPost(postId = postId))
    }

    override fun navigateUp() {
        sendUiEffect(PostDetailsEffects.NavigateUp)
    }

    override fun onClickPhoneButton() {
        sendUiEffect(PostDetailsEffects.NavigateToPhone)
    }

    override fun onClickWhatsappButton() {
        sendUiEffect(PostDetailsEffects.NavigateToWhatsapp)
    }

    override fun onClickMessageButton() {
        sendUiEffect(PostDetailsEffects.NavigateToMessages)
    }

    override fun onClickRetry() {
        getPostDetails()
    }

}
