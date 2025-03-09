package com.example.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.category.GetCategoriesUseCase
import com.example.domain.model.CategoryItem
import com.example.domain.model.PostItem
import com.example.domain.search.GetSearchResultUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.models.ChipUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : BaseViewModel<SearchUiState, SearchEffects>(SearchUiState()), ISearchInteractions {
    private val args = SearchArgs(savedStateHandle)

    init {
        prepareChipsList()
        viewModelScope.launch {
            _state.map { it.data.search }.debounce(500L).distinctUntilChanged()
                .collect { if (it.isNotBlank()) search() }
        }
    }

    private fun prepareChipsList() {
        tryToExecute(
            call = { getCategoriesUseCase() },
            onSuccess = ::onGetChipsDataSuccess,
        )
    }

    private fun onGetChipsDataSuccess(categoryItems: List<CategoryItem>) {
        val chipsList = List(categoryItems.size) { index ->
            if (args.filterCategoryItem == categoryItems[index]) {
                ChipUiState(
                    categoryItem = categoryItems[index],
                    selected = true,
                    onClick = { search() })
                    .also { search() }
            } else {
                ChipUiState(
                    categoryItem = categoryItems[index],
                    selected = false,
                    onClick = { search() })
            }
        }
        updateData {
            copy(filterChipsList = chipsList)
        }
    }

    private fun search() {
        tryToExecute(
            call = {
                updateErrorMessage()
                updateData {
                    copy(topicsList = listOf())
                }
                val filterChips =
                    _state.value.data.filterChipsList.map { it.toChip() }
//                    _state.value.data.filterChipsList.filter { it.selected }.map { it.text }
                getSearchResultUseCase(_state.value.data.search, filterChips)
            },
            onSuccess = ::onSearchSuccess,
            onError = ::onSearchFail
        )
    }

    private fun onSearchSuccess(data: List<PostItem>) {
        updateData {
            copy(topicsList = data)
        }
    }

    private fun onSearchFail(throwable: Throwable) {
        onActionFail(throwable)
        updateData {
            copy(topicsList = listOf())
        }
    }


    override fun onSearchChange(newValue: String) {
        updateData {
            copy(search = newValue)
        }
    }

    override fun onClickTryAgain() {
        search()
    }

    override fun navigateToPostDetails(postId: String) {
        navigateTo(SearchEffects.NavigateToPostDetails(postId))
    }

}