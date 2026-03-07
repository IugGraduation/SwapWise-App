package com.example.ui.models

import com.example.domain.model.LocationItem
import com.example.domain.model.PostItem

data class PostItemUiState(
    val postItem: AsyncState<PostItem> = AsyncState.Initial,
    val locationDropdown: AsyncState<List<LocationItem>> = AsyncState.Initial,
    val selectedLocation: LocationItem? = null,
    val categories: AsyncState<List<ChipUiState>> = AsyncState.Initial,
    val favoriteCategories: AsyncState<List<ChipUiState>> = AsyncState.Initial,
    val showEditPostButton: Boolean = false,
    val postError: PostErrorUiState = PostErrorUiState(),
)

data class PostErrorUiState(
    val imageError: String = "",
    val titleError: String = "",
    val locationError: String = "",
    val detailsError: String = "",
    val categoryError: String = "",
)
