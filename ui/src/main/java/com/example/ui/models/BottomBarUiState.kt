package com.example.ui.models

import androidx.navigation.NavController

data class BottomBarUiState(
    val selectedItem: Int = 0,
    val onItemSelected: (Int) -> Unit = {},
    val navController: NavController? = null,
)
