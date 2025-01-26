package com.example.ui.models

import com.example.domain.model.PostItem
import com.example.domain.model.UiState

data class PostItemUiState(
    val postItem: PostItem = PostItem(),
    val shouldNavigateUp: Boolean = false,
    val chipsList: List<Chip> = listOf(),
    val favoriteChipsList: List<Chip> = listOf(),

    override val isLoading: Boolean = false,
    override val error: String? = null,
) : UiState
