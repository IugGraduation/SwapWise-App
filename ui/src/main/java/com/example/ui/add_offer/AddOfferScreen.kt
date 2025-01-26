package com.example.ui.add_offer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AddOfferScreen(navController: NavController, viewModel: AddOfferViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.shouldNavigateUp) {
        if (state.shouldNavigateUp) navController.navigateUp()
    }

    AddOfferContent(
        state = state,
        onTitleChange = viewModel::onTitleChange,
        onPlaceChange = viewModel::onPlaceChange,
        onDetailsChange = viewModel::onDetailsChange,
        onClickAddOffer = viewModel::onClickAddOffer,
        onSelectedImageChange = viewModel::onSelectedImageChange,
        onClickGoBack = { navController.navigateUp() },
    )
}

