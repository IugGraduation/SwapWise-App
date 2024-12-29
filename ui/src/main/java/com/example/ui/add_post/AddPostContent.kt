package com.example.ui.add_post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.GetCategoriesNamesUseCase
import com.example.domain.GetCategoriesUseCase
import com.example.domain.model.PostItem
import com.example.ui.R
import com.example.ui.components.atoms.CustomButton
import com.example.ui.components.atoms.CustomTextFieldIcon
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.DetailsPageImage
import com.example.ui.components.molecules.SimpleCustomMultilineTextField
import com.example.ui.components.molecules.SimpleCustomTextField
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
fun AddPostContent(
    state: PostItemUiState,
    onTitleChange: (String) -> Unit,
    onPlaceChange: (String) -> Unit,
    onDetailsChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onFavoriteCategoryChange: (String) -> Unit,
    onClickAddPost: () -> Unit,
    onClickAddImage: () -> Unit,
    onClickGoBack: () -> Unit
) {
    TitledScreenTemplate(
        title = stringResource(R.string.add_post),
        onClickGoBack = onClickGoBack,
        floatingActionButton = {
            CustomButton(
                onClick = onClickAddPost,
                text = stringResource(R.string.post),
                modifier = Modifier.padding(horizontal = Spacing16)
            )
        },
        contentState = state
    ) {
        DetailsPageImage(state.postItem.image, onClick = onClickAddImage)
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
            SimpleCustomTextField(
                value = state.postItem.title,
                onValueChange = onTitleChange,
                placeholder = stringResource(R.string.post_title),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_title)
                    )
                },
                errorMessage = state.postItem.titleError,
            )
            VerticalSpacer(Spacing8)
            SimpleCustomTextField(
                value = state.postItem.place,
                onValueChange = onPlaceChange,
                placeholder = stringResource(R.string.your_place),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_location)
                    )
                },
                errorMessage = state.postItem.placeError,
            )
            VerticalSpacer(Spacing8)
            SimpleCustomMultilineTextField(
                value = state.postItem.details,
                onValueChange = onDetailsChange,
                placeholder = stringResource(R.string.details),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_details)
                    )
                },
                errorMessage = state.postItem.detailsError,
            )
            VerticalSpacer(Spacing24)
            TitledChipsList(
                title = stringResource(R.string.categories_of_your_post),
                textStyle = TextStyles.headingLarge,
                chipsList = state.postItem.allCategories.map {
                    Chip(
                        text = it,
                        onClick = onCategoryChange,
                        selected = it == state.postItem.category
                    )
                },
            )
            VerticalSpacer(Spacing24)
            TitledChipsList(
                title = stringResource(R.string.categories_you_like),
                textStyle = TextStyles.headingLarge,
                chipsList = state.postItem.allCategories.map {
                    Chip(
                        text = it,
                        onClick = onFavoriteCategoryChange,
                        selected = it == state.postItem.category
                    )
                },
            )

        }

    }
}


@Preview(showBackground = true, device = "spec:width=1080px,height=2540px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        AddPostContent(
            state = PostItemUiState(
                postItem = PostItem(
                    category = "Category",
                    favoriteCategories = GetCategoriesNamesUseCase(
                        GetCategoriesUseCase()
                    ).getFakeData().toMutableList(),
                    allCategories = GetCategoriesNamesUseCase(
                        GetCategoriesUseCase()
                    ).getFakeData().toMutableList()
                )
            ),
            onClickAddPost = { },
            onClickGoBack = { },
            onTitleChange = { },
            onPlaceChange = { },
            onDetailsChange = { },
            onClickAddImage = { },
            onCategoryChange = { },
            onFavoriteCategoryChange = { },
        )
    }
}