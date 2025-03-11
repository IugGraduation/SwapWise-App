package com.example.ui.home

import com.example.domain.authentication.GetAuthUseCase
import com.example.domain.home.GetHomeDataUseCase
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
    getHomeDataUseCase: GetHomeDataUseCase,
    private val getAuthUseCase: GetAuthUseCase
) :
    BaseViewModel<HomeUiState, HomeEffects>(HomeUiState()), IHomeInteractions {

    override fun navigateToAddPost(postTitle: String) {
        navigateTo(HomeEffects.NavigateToAddPost(postTitle))
    }

    override fun onClickGoToDetails(topicItem: TopicItem) {
        if (topicItem is CategoryItem) {
            navigateTo(HomeEffects.NavigateToSearchByCategory(topicItem.uuid))
        } else if (topicItem is PostItem) {
            tryToExecute(
                call = { if (topicItem.user.uuid != getAuthUseCase().userId) throw Exception() },
                onSuccess = { navigateTo(HomeEffects.NavigateToEditPost(topicItem.uuid)) },
                onError = { navigateTo(HomeEffects.NavigateToPostDetails(topicItem.uuid)) },
            )
        }
    }


    init {
        tryToExecute(
            call = { getHomeDataUseCase() },
            onSuccess = ::onGetHomeDataSuccess,
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

}