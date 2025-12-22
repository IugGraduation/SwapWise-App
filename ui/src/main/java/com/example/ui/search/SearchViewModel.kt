package com.example.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.domain.category.GetCategoriesUseCase
import com.example.domain.location.GetLocationsUseCase
import com.example.domain.model.CategoryItem
import com.example.domain.model.LocationItem
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
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
) : BaseViewModel<SearchUiState, SearchEffects>(SearchUiState()), ISearchInteractions {

    init {
        loadAllFilters()
        viewModelScope.launch {
            _state.map { it.data.search }.debounce(500L).distinctUntilChanged()
                .collect { if (it.isNotBlank()) search() }
        }
    }

    private fun loadAllFilters() {
        tryToExecute(
            call = {
                val categories = getCategoriesUseCase()
                val locations = getLocationsUseCase()
                Pair(categories, locations)
            },
            onSuccess = ::onLoadFiltersSuccess,
        )
    }

    private fun onLoadFiltersSuccess(filters: Pair<List<CategoryItem>, List<LocationItem>>) {
        val (categories, locations) = filters
        
        val categoryChips = categories.map { category ->
            ChipUiState(
                categoryItem = category,
                selected = mutableStateOf(false),
                onClick = { search() }
            )
        }
        
        val locationChips = locations.map { location ->
            ChipUiState(
                categoryItem = CategoryItem(id = location.id, name = location.name),
                selected = mutableStateOf(false),
                onClick = { search() }
            )
        }
        
        updateData {
            copy(
                categoryFilterChipsList = categoryChips,
                locationFilterChipsList = locationChips
            )
        }
    }

    private fun search() {
        val searchVal = _state.value.data.search
        val categoryIds = _state.value.data.categoryFilterChipsList.filter { it.selected.value }
            .map { it.categoryItem.id }
        val locationIds = _state.value.data.locationFilterChipsList.filter { it.selected.value }
            .map { it.categoryItem.id }

        if (searchVal.isBlank() && categoryIds.isEmpty() && locationIds.isEmpty()) return

        tryToExecute(
            call = {
                updateErrorMessage()
                updateData { copy(topicsList = listOf()) }
                getSearchResultUseCase(
                    searchValue = searchVal,
                    categoryIdsFilter = categoryIds,
                    locationIdsFilter = locationIds
                )
            },
            onSuccess = ::onSearchSuccess,
            onError = ::onSearchFail
        )
    }

    private fun onSearchSuccess(data: List<PostItem>) {
        updateData {
            copy(topicsList = data, emptyResult = data.isEmpty())
        }
    }

    private fun onSearchFail(throwable: Throwable) {
        onActionFail(throwable)
        updateData {
            copy(topicsList = listOf(), emptyResult = true)
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
        sendUiEffect(SearchEffects.NavigateToPostDetails(postId))
    }

}