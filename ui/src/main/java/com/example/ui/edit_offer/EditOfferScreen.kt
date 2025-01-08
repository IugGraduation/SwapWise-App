package com.example.ui.edit_offer

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun EditOfferScreen(navController: NavController, viewModel: EditOfferViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.shouldNavigateUp) {
        if (state.shouldNavigateUp) navController.navigateUp()
    }

    EditOfferContent(
        state = state,
        onTitleChange = viewModel::onTitleChange,
        onPlaceChange = viewModel::onPlaceChange,
        onDetailsChange = viewModel::onDetailsChange,
        onSelectedImageChange = viewModel::onSelectedImageChange,
        onClickSave = viewModel::onClickSave,
        onClickDelete = viewModel::onClickDelete,
        onClickGoBack = { navController.navigateUp() },
    )
}

