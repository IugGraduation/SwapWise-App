package com.sam.ui.home

import com.sam.domain.home.GetHomeDataUseCase
import com.sam.domain.home.GetPostsFromCategoryUseCase
import com.sam.domain.model.CategoryItem
import com.sam.domain.model.PostItem
import com.sam.domain.model.TopicItem
import com.sam.ui.base.BaseViewModel
import com.sam.ui.models.AsyncState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val getPostsFromCategoryUseCase: GetPostsFromCategoryUseCase,
) :
    BaseViewModel<HomeUiState, HomeEffects>(HomeUiState()), IHomeInteractions {


    fun onResume() {
        getHomeData()
    }

    private fun getHomeData() {
        val currentState = _state.value.data.homeData

        tryToExecuteAsync(
            call = { getHomeDataUseCase() },
            stateUpdater = { newState ->
                val homeDataState = newState.mapData { it.toHomeDataUI() }

                if (currentState is AsyncState.Success) {
                    if (homeDataState is AsyncState.Success) {
                        updateData { copy(homeData = homeDataState) }
                    }
                } else {
                    updateData { copy(homeData = homeDataState) }
                }
            }
        )
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

    override fun onRetryHome() {
        onResume()
    }

}
