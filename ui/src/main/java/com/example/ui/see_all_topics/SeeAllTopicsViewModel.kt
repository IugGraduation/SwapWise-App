package com.example.ui.see_all_topics

import androidx.lifecycle.SavedStateHandle
import com.example.domain.model.TopicsHolder
import com.example.domain.home.SeeAllTopicsUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.INavigateUp
import com.example.ui.base.MyUiState
import com.example.ui.models.TopicsHolderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeeAllTopicsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    seeAllTopicsUseCase: SeeAllTopicsUseCase,
) : BaseViewModel<TopicsHolderUiState, SeeAllTopicsEffects>(TopicsHolderUiState()), INavigateUp {
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


    fun navigateToPostDetails(postId: String) {
        navigateTo(SeeAllTopicsEffects.NavigateToPostDetails(postId))
    }

    fun navigateToSearch(filterCategoryName: String = "") {
        navigateTo(SeeAllTopicsEffects.NavigateToSearch(filterCategoryName))
    }

    override fun navigateUp() {
        navigateTo(SeeAllTopicsEffects.NavigateUp)
    }
}
