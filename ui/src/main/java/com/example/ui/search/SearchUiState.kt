package com.example.ui.search

import com.example.domain.model.PostItem
import com.example.ui.models.ChipUiState


data class SearchUiState(
    val search: String = "",
    val topicsList: List<PostItem> = listOf(),
    val emptyResult: Boolean = false,
    val filterChipsList: List<ChipUiState> = listOf(),
)