package com.sam.ui.see_all_topics

import androidx.lifecycle.SavedStateHandle
import com.sam.domain.home.GetPostsFromCategoryUseCase
import com.sam.domain.home.SeeAllTopicsUseCase
import com.sam.domain.model.CategoryItem
import com.sam.domain.model.PostItem
import com.sam.domain.model.TopicItem
import com.sam.domain.model.TopicsHolder
import com.sam.ui.base.BaseViewModel
import com.sam.ui.base.MyUiState
import com.sam.ui.models.TopicsHolderUiState
import com.sam.ui.models.toTopicsHolderUiState
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
                call = { getPostsFromCategoryUseCase(topicItem.id, topicItem.name) },
                onSuccess = {
                    sendUiEffect(
                        SeeAllTopicsEffects.NavigateSeeAllTopics(topicItem.id, topicItem.name)
                    )
                },
            )

        } else if (topicItem is PostItem) {
            sendUiEffect(SeeAllTopicsEffects.NavigateToPostDetails(topicItem.id))
        }
    }

    override fun navigateUp() {
        sendUiEffect(SeeAllTopicsEffects.NavigateUp)
    }
}
