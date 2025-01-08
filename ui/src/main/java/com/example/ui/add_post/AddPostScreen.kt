package com.example.ui.add_post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AddPostScreen(navController: NavController, viewModel: AddPostViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.shouldNavigateUp) {
        if (state.shouldNavigateUp) navController.navigateUp()
    }

    AddPostContent(
        state = state,
        onTitleChange = viewModel::onTitleChange,
        onPlaceChange = viewModel::onPlaceChange,
        onDetailsChange = viewModel::onDetailsChange,
        onClickAddPost = viewModel::onClickAddPost,
        onSelectedImageChange = viewModel::onSelectedImageChange,
        onClickGoBack = { navController.navigateUp() },
    )
}
