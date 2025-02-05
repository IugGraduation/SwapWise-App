package com.example.ui.search

import androidx.lifecycle.viewModelScope
import com.example.domain.GetCategoriesNamesUseCase
import com.example.domain.GetSearchResultUseCase
import com.example.domain.model.PostItem
import com.example.ui.base.BaseViewModel
import com.example.ui.models.Chip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val getCategoriesNamesUseCase: GetCategoriesNamesUseCase
) : BaseViewModel<SearchUiState>(SearchUiState()), ISearchInteractions {

    init {
        prepareChipsList()
        viewModelScope.launch {
            _state.map { it.search }.debounce(500L).distinctUntilChanged()
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
            Chip(text = categoriesNames[index], selected = false, onClick = { search() })
        }
        _state.update {
            it.copy(filterChipsList = chipsList)
        }
    }

    private fun search() {
        tryToExecute(
            call = {
                updateErrorMessage()
                _state.update { it.copy(topicsList = listOf()) }
                val filterCategories = _state.value.filterChipsList.map { it.text }
                getSearchResultUseCase(_state.value.search, filterCategories)
            },
            onSuccess = ::onSearchSuccess,
            onError = ::onSearchFail
        )
    }

    private fun onSearchSuccess(data: List<PostItem>) {
        _state.update { it.copy(topicsList = data) }
    }

    private fun onSearchFail(throwable: Throwable) {
        onActionFail(throwable)
        _state.update { it.copy(topicsList = listOf()) }
    }


    override fun onSearchChange(newValue: String) {
        _state.update { it.copy(search = newValue) }
    }

    override fun onClickTryAgain() {
        search()
    }

}