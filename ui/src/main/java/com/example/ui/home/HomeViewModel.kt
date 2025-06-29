package com.example.ui.home

import com.example.domain.home.GetHomeDataUseCase
import com.example.domain.home.GetPostsFromCategoryUseCase
import com.example.domain.model.CategoryItem
import com.example.domain.model.Home
import com.example.domain.model.PostItem
import com.example.domain.model.TopicItem
import com.example.ui.base.BaseViewModel
import com.example.ui.base.MyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val getPostsFromCategoryUseCase: GetPostsFromCategoryUseCase,
) :
    BaseViewModel<HomeUiState, HomeEffects>(HomeUiState()), IHomeInteractions {

    init {
        isActionLoading(isLoading = true, shouldHideContent = true)
    }


    fun onResume() {
        getHomeData()
    }

    private fun getHomeData() {
        tryToExecute(
            call = { getHomeDataUseCase() },
            onSuccess = ::onGetHomeDataSuccess,
            shouldLoad = _state.value.data.user.name.isBlank(),
            shouldHideContent = _state.value.data.user.name.isBlank(),
        )
    }

    private fun onGetHomeDataSuccess(data: Home) {
        _state.value = MyUiState(data.toHomeUiState())
    }


    override fun onNewPostFieldChange(newValue: String) {
        updateData {
            copy(newPost = newValue)
        }
    }

    override fun navigateToAddPost(postTitle: String) {
        sendUiEffect(HomeEffects.NavigateToAddPost(postTitle))
    }

    override fun onClickGoToDetails(topicItem: TopicItem) {
        if (topicItem is CategoryItem) {
            tryToExecute(
                call = { getPostsFromCategoryUseCase(topicItem.id, topicItem.name) },
                onSuccess = {
                    sendUiEffect(
                        HomeEffects.NavigateSeeAllTopics(
                            topicItem.id,
                            topicItem.name
                        )
                    )
                },
            )
        } else if (topicItem is PostItem) {
            sendUiEffect(HomeEffects.NavigateToPostDetails(topicItem.id))
        }
    }


}