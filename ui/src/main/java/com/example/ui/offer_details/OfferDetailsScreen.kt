package com.example.ui.offer_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun OfferDetailsScreen(
    navController: NavController,
    viewModel: OfferDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    OfferDetailsContent(
        state = state,
        onClickGoBack = { navController.navigateUp() },
    )
}