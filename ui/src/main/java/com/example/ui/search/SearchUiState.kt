package com.example.ui.search

import com.example.domain.model.PostItem
import com.example.ui.models.AsyncState
import com.example.ui.models.ChipUiState

data class SearchUiState(
    val search: String = "",
    val topicsList: AsyncState<List<PostItem>> = AsyncState.Initial,
    val categoriesFilter: AsyncState<List<ChipUiState>> = AsyncState.Initial,
    val locationsFilter: AsyncState<List<ChipUiState>> = AsyncState.Initial,
    val emptyResult: Boolean = false,
)
