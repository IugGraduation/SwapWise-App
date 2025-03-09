package com.example.ui.edit_post

import android.net.Uri
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.model.CategoryItem
import com.example.domain.post.GetFakePostDetailsUseCase
import com.example.ui.R
import com.example.ui.base.MyUiState
import com.example.ui.base.NavigateUpEffect
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.SwapWiseOutlineButton
import com.example.ui.components.atoms.SwapWiseTextField
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.ProductImage
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.PostItemUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Primary
import com.example.ui.theme.Spacing12
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun EditPostScreen(navController: NavController, viewModel: EditPostViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NavigateUpEffect.NavigateUp -> navController.navigateUp()
            }
        }
    }

    EditOfferContent(
        state = state,
        editInteractions = viewModel,
    )
}

@Composable
fun EditOfferContent(
    state: MyUiState<PostItemUiState>,
    editInteractions: IEditPostInteractions,
) {
    TitledScreenTemplate(
        title = stringResource(R.string.edit_post),
        onClickGoBack = editInteractions::navigateUp,
        baseUiState = state.baseUiState,
    ) {
        ProductImage(
            state.data.postItem.imageLink, onImagePicked = editInteractions::onSelectedImageChange
        )
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(Spacing16),
            verticalArrangement = Arrangement.spacedBy(Spacing8)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.post_info),
                    style = TextStyles.headingLarge,
                    color = MaterialTheme.color.textPrimary
                )
                OpenClosedSwitch(
                    isOpen = state.data.postItem.isOpen,
                    onIsOpenChange = editInteractions::onIsOpenChange
                )
            }

            SwapWiseTextField(
                value = state.data.postItem.title,
                onValueChange = editInteractions::onTitleChange,
                placeholder = stringResource(R.string.post_title),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_title),
                        contentDescription = stringResource(R.string.post_title),
                        tint = MaterialTheme.color.textTertiary
                    )
                },
                errorMessage = state.data.postError.titleError,
            )

            SwapWiseTextField(
                value = state.data.postItem.place,
                onValueChange = editInteractions::onPlaceChange,
                placeholder = stringResource(R.string.your_place),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = stringResource(R.string.your_place),
                        tint = MaterialTheme.color.textTertiary
                    )
                },
                errorMessage = state.data.postError.placeError,
            )
            SwapWiseTextField(
                value = state.data.postItem.details,
                onValueChange = editInteractions::onDetailsChange,
                placeholder = stringResource(R.string.details),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_details),
                        contentDescription = stringResource(R.string.details),
                        tint = MaterialTheme.color.textTertiary
                    )
                },
                errorMessage = state.data.postError.detailsError,
                isMultiline = true,
            )
            VerticalSpacer(Spacing16)
            TitledChipsList(
                title = stringResource(R.string.category_of_your_post),
                textStyle = TextStyles.headingLarge,
                chipsList = state.data.chipsList.onEach {
                    it.selected = it.categoryItem == state.data.postItem.categoryItem
                },
            )
            VerticalSpacer(Spacing16)
            TitledChipsList(
                title = stringResource(R.string.categories_you_like),
                textStyle = TextStyles.headingLarge,
                chipsList = state.data.favoriteChipsList.onEach {
                    it.selected =
                        state.data.postItem.favoriteCategoryItems.contains(it.categoryItem)
                },
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = Spacing16, bottom = Spacing24),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                SwapWiseFilledButton(
                    onClick = editInteractions::onClickSave,
                    text = stringResource(R.string.save),
                )
                VerticalSpacer(Spacing8)
                SwapWiseOutlineButton(
                    onClick = editInteractions::onClickDelete,
                    text = stringResource(R.string.delete),
                )
            }
        }
    }
}

@Composable
private fun OpenClosedSwitch(isOpen: Boolean, onIsOpenChange: (Boolean) -> Unit) {
    Row {
        val onBackgroundColor = MaterialTheme.color.onBackground
        val secondaryColor = MaterialTheme.color.textSecondary

        val openContainerColor = remember { Animatable(Primary) }
        val openContentColor = remember { Animatable(Color.White) }

        val closedContainerColor = remember { Animatable(onBackgroundColor) }
        val closedContentColor = remember { Animatable(secondaryColor) }


        LaunchedEffect(key1 = isOpen) {
            when (isOpen) {
                true -> {
                    openContainerColor.animateTo(Primary)
                    openContentColor.animateTo(Color.White)

                    closedContainerColor.animateTo(onBackgroundColor)
                    closedContentColor.animateTo(secondaryColor)
                }

                false -> {
                    openContainerColor.animateTo(onBackgroundColor)
                    openContentColor.animateTo(secondaryColor)

                    closedContainerColor.animateTo(Primary)
                    closedContentColor.animateTo(Color.White)
                }
            }
        }
        Box(
            Modifier
                .clip(
                    RoundedCornerShape(
                        topStartPercent = 100, bottomStartPercent = 100
                    )
                )
                .background(color = openContainerColor.value)
                .padding(Spacing12)
                .clickable { onIsOpenChange(true) }

        ) {
            Text(
                stringResource(R.string.open),
                style = TextStyles.headingMedium,
                color = openContentColor.value
            )
        }

        Box(
            Modifier
                .clip(RoundedCornerShape(topEndPercent = 100, bottomEndPercent = 100))
                .background(color = closedContainerColor.value)
                .padding(Spacing12)
                .clickable { onIsOpenChange(false) }) {
            Text(
                stringResource(R.string.closed),
                style = TextStyles.headingMedium,
                color = closedContentColor.value
            )
        }
    }
}


@Preview(showBackground = true, device = "spec:width=1080px,height=3040px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        EditOfferContent(
            state = MyUiState(
                PostItemUiState(
            postItem = GetFakePostDetailsUseCase()().copy(
                categoryItem = CategoryItem("Food and beverages0"),
            )
                )
        ), editInteractions = object : IEditPostInteractions {
            override fun onTitleChange(title: String) {}
            override fun onPlaceChange(place: String) {}
            override fun onDetailsChange(details: String) {}
            override fun onIsOpenChange(isOpen: Boolean) {}
            override fun onSelectedImageChange(selectedImageUri: Uri) {}
            override fun onClickSave() {}
            override fun onClickDelete() {}
                override fun navigateUp() {}
            },
        )
    }
}