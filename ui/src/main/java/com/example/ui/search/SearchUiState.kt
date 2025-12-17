package com.example.ui.search

import com.example.domain.model.PostItem
import com.example.ui.models.ChipUiState

data class SearchUiState(
    val search: String = "",
    val topicsList: List<PostItem> = listOf(),
    val categoryFilterChipsList: List<ChipUiState> = listOf(),
    val locationFilterChipsList: List<ChipUiState> = listOf(),
    val emptyResult: Boolean = false,
)
