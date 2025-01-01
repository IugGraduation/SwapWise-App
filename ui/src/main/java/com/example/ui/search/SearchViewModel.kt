package com.example.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetCategoriesNamesUseCase
import com.example.domain.GetSearchResultUseCase
import com.example.domain.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) :
    ViewModel() {
    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val categoriesNames = getCategoriesNamesUseCase()
            _state.value.filterChipsList.forEachIndexed { index, chip ->
                chip.text = categoriesNames[index]
                chip.selected = false
                chip.onClick = {
                    chip.selected = !chip.selected
                    search()
                }
            }

            _state.map { it.search }.debounce(500L).distinctUntilChanged()
                .collect { if (it.isNotBlank()) search() }
        }
    }

    private fun search() {
        viewModelScope.launch {
            getSearchResultUseCase(_state.value.search).collect { searchResult ->
                when (searchResult) {
                    is State.Loading -> _state.update {
                        it.copy(
                            isLoading = true,
                            topicsList = listOf(),
                            error = null
                        )
                    }

                    is State.Success -> _state.update {
                        it.copy(
                            isLoading = false,
                            topicsList = searchResult.data,
                            error = null
                        )
                    }

                    is State.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            topicsList = listOf(),
                            error = searchResult.message
                        )
                    }
                }
            }
        }
    }


    fun onSearchChange(newValue: String) {
        _state.update { it.copy(search = newValue) }
    }

    fun onClickTryAgain() {
        search()
    }

}