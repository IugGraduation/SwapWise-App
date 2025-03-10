package com.example.ui.see_all_topics

import androidx.lifecycle.SavedStateHandle
import com.example.domain.home.SeeAllTopicsUseCase
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
) : BaseViewModel<TopicsHolderUiState, SeeAllTopicsEffects>(TopicsHolderUiState()),
    ISeeAllInteractions {
    private val args = SeeAllTopicsArgs(savedStateHandle)

    init {
        tryToExecute(
            call = { seeAllTopicsUseCase(args.url) },
            onSuccess = ::onGetHomeDataSuccess,
        )
    }

    private fun onGetHomeDataSuccess(data: TopicsHolder) {
        _state.value = MyUiState(TopicsHolderUiState.fromTopicsHolder(data))
    }


    override fun onClickGoToDetails(topicId: String, isCategory: Boolean) {
        if (isCategory) {
            sendUiEffect(SeeAllTopicsEffects.NavigateToSearchByCategory(topicId))
        } else {
            sendUiEffect(SeeAllTopicsEffects.NavigateToPostDetails(topicId))

        }
    }

    override fun navigateUp() {
        sendUiEffect(SeeAllTopicsEffects.NavigateUp)
    }
}
