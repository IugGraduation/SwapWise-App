package com.example.ui.post_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ui.components.atoms.ShowContentWithState

@Composable
fun PostDetailsScreen(
    navController: NavController,
    viewModel: PostDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ShowContentWithState(state) {
        PostDetailsContent(
            state = state,
            onClickAddOffer = {},
            onClickGoBack = { navController.navigateUp() },
        )
    }

}