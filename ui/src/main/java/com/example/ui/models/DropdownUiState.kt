package com.example.ui.models

data class DropdownUiState<T>(
    val selectedItem: T? = null,
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
