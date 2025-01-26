package com.example.ui.models

import com.example.domain.model.PostItem
import com.example.ui.base.BaseUiState

data class PostItemUiState(
    val postItem: PostItem = PostItem(),
    val shouldNavigateUp: Boolean = false,
    val chipsList: List<Chip> = listOf(),
    val favoriteChipsList: List<Chip> = listOf(),

    val baseUiState: BaseUiState = BaseUiState()
)
