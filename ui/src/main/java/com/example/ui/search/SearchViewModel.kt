package com.example.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.domain.category.GetCategoriesUseCase
import com.example.domain.location.GetLocationsUseCase
import com.example.domain.model.CategoryItem
import com.example.domain.search.GetSearchResultUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.models.AsyncState
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
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
) : BaseViewModel<SearchUiState, SearchEffects>(SearchUiState()), ISearchInteractions {

    init {
        loadCategoryFilters()
        loadLocationFilters()
        viewModelScope.launch {
            _state.map { it.data.search }.debounce(500L).distinctUntilChanged()
                .collect { search() }
        }
    }

    private fun loadCategoryFilters() {
        tryToExecuteAsync(
            call = { getCategoriesUseCase() },
            stateUpdater = { newState ->
                val chips = newState.mapData { categories ->
                    categories.map { category ->
                        ChipUiState(
                            categoryItem = category,
                            selected = mutableStateOf(false),
                            onClick = { search() }
                        )
                    }
                }
                updateData { copy(categoriesFilter = chips) }
            }
        )
    }

    private fun loadLocationFilters() {
        tryToExecuteAsync(
            call = { getLocationsUseCase() },
            stateUpdater = { newState ->
                val chips = newState.mapData { locations ->
                    locations.map { location ->
                        ChipUiState(
                            categoryItem = CategoryItem(id = location.id, name = location.name),
                            selected = mutableStateOf(false),
                            onClick = { search() }
                        )
                    }
                }
                updateData { copy(locationsFilter = chips) }
            }
        )
    }

    private fun search() {
        val searchVal = _state.value.data.search
        val categoryIds = _state.value.data.categoriesFilter.data?.filter { it.selected.value }
            ?.map { it.categoryItem.id } ?: emptyList()
        val locationIds = _state.value.data.locationsFilter.data?.filter { it.selected.value }
            ?.map { it.categoryItem.id } ?: emptyList()

        if (searchVal.isBlank() && categoryIds.isEmpty() && locationIds.isEmpty()) {
            updateData { copy(topicsList = AsyncState.Initial) }
            return
        }

        tryToExecuteAsync(
            call = {
                getSearchResultUseCase(
                    searchValue = searchVal,
                    categoryIdsFilter = categoryIds,
                    locationIdsFilter = locationIds
                )
            },
            stateUpdater = { newState ->
                updateData { copy(topicsList = newState) }
            }
        )
    }


    override fun onSearchChange(newValue: String) {
        updateData { copy(search = newValue) }
    }

    override fun onClickTryAgain() {
        search()
    }

    override fun navigateToPostDetails(postId: String) {
        sendUiEffect(SearchEffects.NavigateToPostDetails(postId))
    }

    override fun onRetryCategories() {
        loadCategoryFilters()
    }

    override fun onRetryLocations() {
        loadLocationFilters()
    }

}
