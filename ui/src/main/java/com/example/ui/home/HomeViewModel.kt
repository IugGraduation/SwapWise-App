package com.example.ui.home

import com.example.domain.authentication.GetAuthUseCase
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
    private val getAuthUseCase: GetAuthUseCase,
    private val getPostsFromCategoryUseCase: GetPostsFromCategoryUseCase,
) :
    BaseViewModel<HomeUiState, HomeEffects>(HomeUiState()), IHomeInteractions {

    fun onResume() {
        tryToExecute(
            call = { getHomeDataUseCase() },
            onSuccess = ::onGetHomeDataSuccess,
            shouldLoad = _state.value.data.user.name.isBlank()
        )
    }


    private fun onGetHomeDataSuccess(data: Home) {
        _state.value = MyUiState(HomeUiState.fromHome(data))
    }


    override fun onNewPostFieldChange(newValue: String) {
        updateData {
            copy(newPost = newValue)
        }
    }

    override fun navigateToAddPost(postTitle: String) {
        navigateTo(HomeEffects.NavigateToAddPost(postTitle))
    }

    override fun onClickGoToDetails(topicItem: TopicItem) {
        if (topicItem is CategoryItem) {
            tryToExecute(
                call = { getPostsFromCategoryUseCase(topicItem.uuid, topicItem.title) },
                onSuccess = {
                    navigateTo(
                        HomeEffects.NavigateSeeAllTopics(
                            topicItem.uuid,
                            topicItem.title
                        )
                    )
                },
            )
        } else if (topicItem is PostItem) {
            tryToExecute(
                call = { if (topicItem.user.uuid != getAuthUseCase().userId) throw Exception() },
                onSuccess = { navigateTo(HomeEffects.NavigateToEditPost(topicItem.uuid)) },
                onError = { navigateTo(HomeEffects.NavigateToPostDetails(topicItem.uuid)) },
            )
        }
    }


}