package com.example.ui.search

import com.example.domain.model.PostItem
import com.example.domain.model.UiState
import com.example.ui.models.Chip


data class SearchUiState(
    val search: String = "",
    val topicsList: List<PostItem>? = listOf(),
    val filterChipsList: List<Chip> = listOf(),

    override val isLoading: Boolean = false,
    override val error: String? = null
): UiState