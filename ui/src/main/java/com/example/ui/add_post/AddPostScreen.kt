package com.example.ui.add_post

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.GetFakeCategoriesNamesUseCase
import com.example.domain.model.PostItem
import com.example.ui.R
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.SwapWiseTextField
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.ProductImage
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.Chip
import com.example.ui.models.PostItemUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun AddPostScreen(navController: NavController, viewModel: AddPostViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.baseUiState.shouldNavigateUp) {
        if (state.baseUiState.shouldNavigateUp) navController.navigateUp()
    }

    AddPostContent(
        state = state,
        addInteractions = viewModel,
        onClickGoBack = { navController.navigateUp() },
    )
}


@Composable
fun AddPostContent(
    state: PostItemUiState,
    addInteractions: IAddPostInteractions,
    onClickGoBack: () -> Unit
) {
    TitledScreenTemplate(
        title = stringResource(R.string.add_post),
        onClickGoBack = onClickGoBack,
        floatingActionButton = {
            SwapWiseFilledButton(
                onClick = addInteractions::onClickAdd,
                text = stringResource(R.string.post),
                modifier = Modifier.padding(horizontal = Spacing16)
            )
        },
        baseUiState = state.baseUiState,
    ) {
        ProductImage(state.postItem.imageLink, onImagePicked = addInteractions::onSelectedImageChange)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing16)
        ) {
            Text(
                text = stringResource(R.string.post_info),
                style = TextStyles.headingLarge,
                color = MaterialTheme.colorScheme.primary
            )
            VerticalSpacer(Spacing8)
            SwapWiseTextField(
                value = state.postItem.title,
                onValueChange = addInteractions::onTitleChange,
                placeholder = stringResource(R.string.post_title),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_title),
                        contentDescription = stringResource(R.string.post_title),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                },
                errorMessage = state.postError.titleError,
            )
            VerticalSpacer(Spacing8)
            SwapWiseTextField(
                value = state.postItem.place,
                onValueChange = addInteractions::onPlaceChange,
                placeholder = stringResource(R.string.your_place),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = stringResource(R.string.your_place),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                },
                errorMessage = state.postError.placeError,
            )
            VerticalSpacer(Spacing8)
            SwapWiseTextField(
                value = state.postItem.details,
                onValueChange = addInteractions::onDetailsChange,
                placeholder = stringResource(R.string.details),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_details),
                        contentDescription = stringResource(R.string.details),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                },
                isMultiline = true,
                errorMessage = state.postError.detailsError,
            )
            VerticalSpacer(Spacing24)
            TitledChipsList(
                title = stringResource(R.string.category_of_your_post),
                textStyle = TextStyles.headingLarge,
                chipsList = state.chipsList.onEach {
                    it.selected = it.text == state.postItem.category
                },
            )
            VerticalSpacer(Spacing24)
            TitledChipsList(
                title = stringResource(R.string.categories_you_like),
                textStyle = TextStyles.headingLarge,
                chipsList = state.favoriteChipsList.onEach {
                    it.selected = state.postItem.favoriteCategories.contains(it.text)
                },
            )

        }

    }
}


//@Preview(showBackground = true, device = "spec:width=1080px,height=2540px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        AddPostContent(
            state = PostItemUiState(
                chipsList = GetFakeCategoriesNamesUseCase()().map { title ->
                    Chip(text = title)
                },
                postItem = PostItem(
                    category = "Category",
                )
            ),
            onClickGoBack = { },
            addInteractions = object : IAddPostInteractions{
                override fun onTitleChange(title: String) {}
                override fun onPlaceChange(place: String) {}
                override fun onDetailsChange(details: String) {}
                override fun onSelectedImageChange(selectedImageUri: Uri) {}
                override fun onClickAdd() {}
            },
        )
    }
}