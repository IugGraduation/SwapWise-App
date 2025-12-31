package com.example.ui.search

import com.example.domain.model.PostItem
import com.example.ui.models.ChipsUiState

data class SearchUiState(
    val search: String = "",
    val topicsList: List<PostItem> = listOf(),
    val categoriesFilter: ChipsUiState = ChipsUiState(),
    val locationsFilter: ChipsUiState = ChipsUiState(),
    val emptyResult: Boolean = false,
)
