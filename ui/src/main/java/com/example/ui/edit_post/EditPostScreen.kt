package com.example.ui.edit_post

import android.net.Uri
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.domain.GetFakePostsUseCase
import com.example.ui.R
import com.example.ui.components.atoms.CustomTextFieldIcon
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

@Composable
fun EditPostScreen(navController: NavController, viewModel: EditPostViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.baseUiState.shouldNavigateUp) {
        if (state.baseUiState.shouldNavigateUp) navController.navigateUp()
    }

    EditOfferContent(
        state = state,
        editInteractions = viewModel,
        onClickGoBack = { navController.navigateUp() },
    )
}


@Composable
fun EditOfferContent(
    state: PostItemUiState,
    editInteractions: IEditPostInteractions,
    onClickGoBack: () -> Unit
) {
    TitledScreenTemplate(
        title = stringResource(R.string.edit_post),
        onClickGoBack = onClickGoBack,
//        contentState = state
    ) {
        ProductImage(
            state.postItem.imageLink, onImagePicked = editInteractions::onSelectedImageChange
        )
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .scrollable(scrollState, orientation = Orientation.Vertical)
                .padding(Spacing16)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.post_info),
                    style = TextStyles.headingLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Row {
                    val onBackgroundColor = MaterialTheme.colorScheme.onBackground
                    val secondaryColor = MaterialTheme.colorScheme.secondary

                    val openContainerColor = remember { Animatable(Primary) }
                    val openContentColor = remember { Animatable(Color.White) }

                    val closedContainerColor = remember { Animatable(onBackgroundColor) }
                    val closedContentColor = remember { Animatable(secondaryColor) }


                    LaunchedEffect(key1 = state.postItem.isOpen) {
                        when (state.postItem.isOpen) {
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
                            .clickable { editInteractions.onIsOpenChange(true) }

                    ) {
                        Text(
                            stringResource(R.string.open),
                            style = TextStyles.headingMedium,
                            color = openContentColor.value
                        )
                    }

                    Box(
                        Modifier
                            .clip(
                                RoundedCornerShape(
                                    topEndPercent = 100, bottomEndPercent = 100
                                )
                            )
                            .background(color = closedContainerColor.value)
                            .padding(Spacing12)
                            .clickable { editInteractions.onIsOpenChange(false) }) {
                        Text(
                            stringResource(R.string.closed),
                            style = TextStyles.headingMedium,
                            color = closedContentColor.value
                        )
                    }
                }

            }

            VerticalSpacer(Spacing8)
            SwapWiseTextField(
                value = state.postItem.title,
                onValueChange = editInteractions::onTitleChange,
                placeholder = stringResource(R.string.post_title),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_title)
                    )
                },
                errorMessage = state.postError.titleError,
            )
            VerticalSpacer(Spacing8)

            SwapWiseTextField(
                value = state.postItem.place,
                onValueChange = editInteractions::onPlaceChange,
                placeholder = stringResource(R.string.your_place),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_location)
                    )
                },
                errorMessage = state.postError.placeError,
            )
            VerticalSpacer(Spacing8)
            SwapWiseTextField(
                value = state.postItem.details,
                onValueChange = editInteractions::onDetailsChange,
                placeholder = stringResource(R.string.details),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_details)
                    )
                },
                errorMessage = state.postError.detailsError,
                isMultiline = true,
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = Spacing24),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                SwapWiseFilledButton(
                    onClick = editInteractions::onClickSave,
                    text = stringResource(R.string.save),
                )
                VerticalSpacer(Spacing8)
                SwapWiseOutlineButton (
                    onClick = editInteractions::onClickDelete,
                    text = stringResource(R.string.delete),
                )
            }
        }
    }
}


@Preview(showBackground = true, device = "spec:width=1080px,height=3040px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        EditOfferContent(state = PostItemUiState(
            postItem = GetFakePostsUseCase()()[0].copy(
                category = "Food and beverages0",
            )
        ), editInteractions = object : IEditPostInteractions {
            override fun onTitleChange(title: String) {}
            override fun onPlaceChange(place: String) {}
            override fun onDetailsChange(details: String) {}
            override fun onIsOpenChange(isOpen: Boolean) {}
            override fun onSelectedImageChange(selectedImageUri: Uri) {}
            override fun onClickSave() {}
            override fun onClickDelete() {}
        }, onClickGoBack = { })
    }
}