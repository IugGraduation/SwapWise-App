package com.example.ui.see_all_topics

import androidx.lifecycle.SavedStateHandle
import com.example.domain.home.GetPostsFromCategoryUseCase
import com.example.domain.home.SeeAllTopicsUseCase
import com.example.domain.model.CategoryItem
import com.example.domain.model.PostItem
import com.example.domain.model.TopicItem
import com.example.domain.model.TopicsHolder
import com.example.ui.base.BaseViewModel
import com.example.ui.base.MyUiState
import com.example.ui.models.TopicsHolderUiState
import com.example.ui.models.toTopicsHolderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeeAllTopicsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    seeAllTopicsUseCase: SeeAllTopicsUseCase,
    private val getPostsFromCategoryUseCase: GetPostsFromCategoryUseCase,
) : BaseViewModel<TopicsHolderUiState, SeeAllTopicsEffects>(TopicsHolderUiState()),
    ISeeAllInteractions {
    private val args = SeeAllTopicsArgs(savedStateHandle)

    init {
        if (args.url.isNotBlank()) {
        tryToExecute(
            call = { seeAllTopicsUseCase(args.url, args.title) },
            onSuccess = ::onGetHomeDataSuccess,
        )
        } else {
            tryToExecute(
                call = { getPostsFromCategoryUseCase(args.categoryId, args.title) },
                onSuccess = { topicsHolder -> onGetHomeDataSuccess(topicsHolder) },
            )
        }
    }

    private fun onGetHomeDataSuccess(data: TopicsHolder) {
        _state.value = MyUiState(data.toTopicsHolderUiState())
    }


    override fun onClickGoToDetails(topicItem: TopicItem) {
        if (topicItem is CategoryItem) {
            tryToExecute(
                call = { getPostsFromCategoryUseCase(topicItem.uuid, topicItem.title) },
                onSuccess = {
                    sendUiEffect(
                        SeeAllTopicsEffects.NavigateSeeAllTopics(topicItem.uuid, topicItem.title)
                    )
                },
            )

        } else if (topicItem is PostItem) {
            sendUiEffect(SeeAllTopicsEffects.NavigateToPostDetails(topicItem.uuid))
        }
    }

    override fun navigateUp() {
        sendUiEffect(SeeAllTopicsEffects.NavigateUp)
    }
}
