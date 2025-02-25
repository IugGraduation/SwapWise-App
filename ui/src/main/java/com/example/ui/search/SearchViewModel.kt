package com.example.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.GetCategoriesNamesUseCase
import com.example.domain.GetSearchResultUseCase
import com.example.domain.model.PostItem
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
    private val getCategoriesNamesUseCase: GetCategoriesNamesUseCase
) : BaseViewModel<SearchUiState, Nothing>(SearchUiState()), ISearchInteractions {
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
            call = { getCategoriesNamesUseCase() },
            onSuccess = ::onGetChipsDataSuccess,
        )
    }

    private fun onGetChipsDataSuccess(categoriesNames: List<String>) {
        val chipsList = List(categoriesNames.size) { index ->
            if (args.filterCategoryName == categoriesNames[index]) {
                ChipUiState(text = categoriesNames[index], selected = true, onClick = { search() })
                    .also { search() }
            } else {
                ChipUiState(text = categoriesNames[index], selected = false, onClick = { search() })
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

}