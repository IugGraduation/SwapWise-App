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
        prepareCategoryChips()
        prepareLocationChips()
        viewModelScope.launch {
            _state.map { it.data.search }.debounce(500L).distinctUntilChanged()
                .collect { if (it.isNotBlank()) search() }
        }
    }

    private fun prepareCategoryChips() {
        tryToExecute(
            call = { getCategoriesUseCase() },
            onSuccess = ::onGetCategoryChipsSuccess,
        )
    }

    private fun onGetCategoryChipsSuccess(categoryItems: List<CategoryItem>) {
        val chipsList = categoryItems.map { category ->
            ChipUiState(
                categoryItem = category,
                selected = mutableStateOf(false),
                onClick = { search() }
            )
        }
        updateData {
            copy(categoryFilterChipsList = chipsList)
        }
    }

    private fun prepareLocationChips() {
        tryToExecute(
            call = { getLocationsUseCase() },
            onSuccess = ::onGetLocationChipsSuccess,
        )
    }

    private fun onGetLocationChipsSuccess(locations: List<LocationItem>) {
        val chipsList = locations.map { location ->
            ChipUiState(
                categoryItem = CategoryItem(
                    id = location.id,
                    name = location.name,
                ),
                selected = mutableStateOf(false),
                onClick = { search() }
            )
        }
        updateData {
            copy(locationFilterChipsList = chipsList)
        }
    }

    private fun search() {
        if (_state.value.data.search.isBlank()) return

        tryToExecute(
            call = {
                updateErrorMessage()
                updateData { copy(topicsList = listOf()) }
                val categoryIds =
                    _state.value.data.categoryFilterChipsList.filter { it.selected.value }
                        .map { it.categoryItem.id }
                val locationIds =
                    _state.value.data.locationFilterChipsList.filter { it.selected.value }
                        .map { it.categoryItem.id }
                getSearchResultUseCase(
                    searchValue = _state.value.data.search,
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