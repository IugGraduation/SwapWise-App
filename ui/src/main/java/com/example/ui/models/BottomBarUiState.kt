package com.example.ui.models

data class BottomBarUiState(
    val selectedItem: Int = 0,
    val onItemSelected: (Int) -> Unit = {},
    val navigateToHome: () -> Unit = {},
    val navigateToSearch: () -> Unit = {},
    val navigateToNotifications: () -> Unit = {},
    val navigateToProfile: () -> Unit = {},
)
