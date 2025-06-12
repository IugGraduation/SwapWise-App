package com.example.ui.base

import kotlinx.coroutines.flow.MutableSharedFlow

data class BaseUiState(
    val errorSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(),
    val isLoading: Boolean = false,
    val shouldHideContent: Boolean = false,
)


data class MyUiState<T>(val data: T, val baseUiState: BaseUiState = BaseUiState())