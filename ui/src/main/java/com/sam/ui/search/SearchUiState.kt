package com.sam.ui.search

import com.sam.domain.model.PostItem
import com.sam.ui.models.AsyncState
import com.sam.ui.models.ChipUiState

data class SearchUiState(
    val search: String = "",
    val topicsList: AsyncState<List<PostItem>> = AsyncState.Initial,
    val categoriesFilter: AsyncState<List<ChipUiState>> = AsyncState.Initial,
    val locationsFilter: AsyncState<List<ChipUiState>> = AsyncState.Initial,
    val emptyResult: Boolean = false,
)
