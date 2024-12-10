package com.example.ui.post_details

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.GetPostsUseCase

@Composable
fun PostDetailsScreen(navController: NavController, viewModel: PostDetailsViewModel = hiltViewModel()) {
    PostDetailsContent(
        state = GetPostsUseCase()()[0],
        onClickMakeOffer = {},
        onClickGoBack = {navController.navigateUp()},
    )
}