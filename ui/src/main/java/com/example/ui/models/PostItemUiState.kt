package com.example.ui.models

import com.example.domain.model.PostItem

data class PostItemUiState(
    val postItem: PostItem = PostItem(),
    val chipsList: List<ChipUiState> = listOf(),
    val favoriteChipsList: List<ChipUiState> = listOf(),
    val showEditPostButton: Boolean = false,
    val postError: PostErrorUiState = PostErrorUiState(),
)

data class PostErrorUiState(
    val imageError: String = "",
    val titleError: String = "",
    val placeError: String = "",
    val detailsError: String = "",
    val categoryError: String = "",
)

