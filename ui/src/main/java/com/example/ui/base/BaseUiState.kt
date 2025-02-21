package com.example.ui.base

import com.example.ui.util.empty

data class BaseUiState(
    val errorMessage: String = String.empty(),
    val isLoading: Boolean = false,
    val shouldNavigateUp: Boolean = false,
)


data class MyUiState<T>(val data: T, val baseUiState: BaseUiState = BaseUiState())