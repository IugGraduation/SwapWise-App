package com.example.ui.models

import com.example.domain.model.PostItem

data class PostItemUiState(
    val postItem: PostItem = PostItem(),
    val chipsList: List<Chip> = listOf(),
    val favoriteChipsList: List<Chip> = listOf(),
    val postError: PostErrorUiState = PostErrorUiState(),
)

data class PostErrorUiState(
    val imageError: String = "",
    val titleError: String = "",
    val placeError: String = "",
    val detailsError: String = "",
    val categoryError: String = "",
)

