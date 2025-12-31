package com.example.ui.models

data class ChipsUiState(
    val items: List<ChipUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
