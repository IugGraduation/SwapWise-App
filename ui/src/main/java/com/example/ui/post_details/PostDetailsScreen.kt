package com.example.ui.post_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun PostDetailsScreen(
    navController: NavController,
    viewModel: PostDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    PostDetailsContent(
        state = state,
        onClickAddOffer = {},
        onClickGoBack = { navController.navigateUp() },
    )

}