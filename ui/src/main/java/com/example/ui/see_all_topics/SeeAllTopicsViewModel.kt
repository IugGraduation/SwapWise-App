package com.example.ui.see_all_topics

import androidx.lifecycle.SavedStateHandle
import com.example.domain.authentication.GetAuthUseCase
import com.example.domain.home.SeeAllTopicsUseCase
import com.example.domain.model.CategoryItem
import com.example.domain.model.PostItem
import com.example.domain.model.TopicItem
import com.example.domain.model.TopicsHolder
import com.example.ui.base.BaseViewModel
import com.example.ui.base.MyUiState
import com.example.ui.models.TopicsHolderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeeAllTopicsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    seeAllTopicsUseCase: SeeAllTopicsUseCase,
    private val getAuthUseCase: GetAuthUseCase
) : BaseViewModel<TopicsHolderUiState, SeeAllTopicsEffects>(TopicsHolderUiState()),
    ISeeAllInteractions {
    private val args = SeeAllTopicsArgs(savedStateHandle)

    init {
        tryToExecute(
            call = { seeAllTopicsUseCase(args.url, args.title) },
            onSuccess = ::onGetHomeDataSuccess,
        )
    }

    private fun onGetHomeDataSuccess(data: TopicsHolder) {
        _state.value = MyUiState(TopicsHolderUiState.fromTopicsHolder(data))
    }


    override fun onClickGoToDetails(topicItem: TopicItem) {
        if (topicItem    is CategoryItem) {
            navigateTo(SeeAllTopicsEffects.NavigateToSearchByCategory(topicItem.uuid))
        } else if (topicItem is PostItem) {
            tryToExecute(
                call = { if (topicItem.user.uuid != getAuthUseCase().userId) throw Exception() },
                onSuccess = { navigateTo(SeeAllTopicsEffects.NavigateToEditPost(topicItem.uuid)) },
                onError = { navigateTo(SeeAllTopicsEffects.NavigateToPostDetails(topicItem.uuid)) },
            )
        }
    }

    override fun navigateUp() {
        navigateTo(SeeAllTopicsEffects.NavigateUp)
    }
}
