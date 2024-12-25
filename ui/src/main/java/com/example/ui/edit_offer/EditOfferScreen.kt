package com.example.ui.edit_offer

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun EditOfferScreen(navController: NavController, viewModel: EditOfferViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val pickImageFromGallery = pickImageFromGallery { viewModel.onSelectedImageChange(it) }

    EditOfferContent(
        state = state,
        onTitleChange = viewModel::onTitleChange,
        onPlaceChange = viewModel::onPlaceChange,
        onDetailsChange = viewModel::onDetailsChange,
        onCategoryChange = viewModel::onCategoryChange,
        onClickSave = viewModel::onClickSave,
        onClickDelete = viewModel::onClickDelete,
        onClickAddImage = pickImageFromGallery,
        onClickGoBack = { navController.navigateUp() },
    )
}


@Composable
fun pickImageFromGallery(onImagePicked: (Uri) -> Unit): () -> Unit {
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onImagePicked(it) }
        }

    return { galleryLauncher.launch("image/*") }
}

