package com.example.ui.add_post

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
fun AddPostScreen(navController: NavController, viewModel: AddPostViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val pickImageFromGallery = pickImageFromGallery { viewModel.onSelectedImageChange(it) }

    LaunchedEffect(state.shouldNavigateUp) {
        if (state.shouldNavigateUp) navController.navigateUp()
    }

    AddPostContent(
        state = state,
        onTitleChange = viewModel::onTitleChange,
        onPlaceChange = viewModel::onPlaceChange,
        onDetailsChange = viewModel::onDetailsChange,
        onCategoryChange = viewModel::onCategoryChange,
        onFavoriteCategoryChange = viewModel::onFavoriteCategoryChange,
        onClickAddPost = viewModel::onClickAddPost,
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

