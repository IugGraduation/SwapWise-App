package com.sam.ui.edit_post

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sam.domain.model.CategoryItem
import com.sam.domain.model.LocationItem
import com.sam.domain.post.GetFakePostDetailsUseCase
import com.sam.ui.R
import com.sam.ui.base.MyUiState
import com.sam.ui.components.AsyncContent
import com.sam.ui.components.atoms.DropdownTextField
import com.sam.ui.components.atoms.SwapWiseFilledButton
import com.sam.ui.components.atoms.SwapWiseOutlineButton
import com.sam.ui.components.atoms.SwapWiseTextField
import com.sam.ui.components.atoms.VerticalSpacer
import com.sam.ui.components.molecules.ProductImage
import com.sam.ui.components.molecules.TitledChipsList
import com.sam.ui.components.templates.TitledScreenTemplate
import com.sam.ui.home.navigateToHome
import com.sam.ui.models.AsyncState
import com.sam.ui.models.PostItemUiState
import com.sam.ui.theme.GraduationProjectTheme
import com.sam.ui.theme.Primary
import com.sam.ui.theme.Spacing12
import com.sam.ui.theme.Spacing16
import com.sam.ui.theme.Spacing24
import com.sam.ui.theme.Spacing8
import com.sam.ui.theme.TextStyles
import com.sam.ui.theme.color
import com.sam.ui.util.Screen
import com.sam.ui.util.toByteArray

@Composable
fun EditPostScreen(navController: NavController, viewModel: EditPostViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is EditPostEffects.NavigateUp -> navController.navigateUp()
                is EditPostEffects.NavigateToHome -> {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    EditPostContent(
        state = state,
        editInteractions = viewModel,
    )
}

@Composable
fun EditPostContent(
    state: MyUiState<PostItemUiState>,
    editInteractions: IEditPostInteractions,
) {
    TitledScreenTemplate(
        title = stringResource(R.string.edit_post),
        onClickGoBack = editInteractions::navigateUp,
        baseUiState = state.baseUiState,
    ) {
        AsyncContent(
            state = state.data.postItem,
            onRetry = editInteractions::getPostDetails
        ) { postItem ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                ProductImage(
                    postItem.imageUrl,
                    onImagePicked = editInteractions::onSelectedImageChange
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                            isOpen = postItem.isOpen,
                            onIsOpenChange = editInteractions::onIsOpenChange
                        )
                    }

                    val focusManager = LocalFocusManager.current

                    SwapWiseTextField(
                        value = postItem.name,
                        onValueChange = editInteractions::onTitleChange,
                        placeholder = stringResource(R.string.post_title),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_title),
                                contentDescription = stringResource(R.string.post_title),
                                tint = MaterialTheme.color.textTertiary
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(
                                FocusDirection.Down
                            )
                        }),
                        errorMessage = state.data.postError.titleError,
                    )

                    DropdownTextField(
                        state = state.data.locationDropdown,
                        selectedItem = state.data.selectedLocation,
                        onValueChange = editInteractions::onLocationChange,
                        onRetry = editInteractions::onRetryLocations,
                        placeholder = stringResource(R.string.your_place),
                        valueToString = { it.name },
                        errorMessage = state.data.postError.locationError,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_location),
                                contentDescription = stringResource(R.string.your_place),
                                tint = MaterialTheme.color.textTertiary
                            )
                        }
                    )

                    SwapWiseTextField(
                        value = postItem.details,
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
                        state = state.data.categories,
                        onRetry = editInteractions::onRetryCategories
                    )
                    VerticalSpacer(Spacing16)
                    TitledChipsList(
                        title = stringResource(R.string.categories_you_like),
                        textStyle = TextStyles.headingLarge,
                        state = state.data.favoriteCategories,
                        onRetry = editInteractions::onRetryCategories
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = Spacing16, bottom = Spacing24),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        val context = LocalContext.current
                        SwapWiseFilledButton(
                            onClick = {
                                val imageByteArray = postItem.imageUrl.toByteArray(context)
                                editInteractions.onClickSave(imageByteArray)
                            },
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
        EditPostContent(
            state = MyUiState(
                PostItemUiState(
                    postItem = AsyncState.Success(
                        GetFakePostDetailsUseCase()().copy(
                            categoryItem = CategoryItem("Food and beverages0"),
                        )
                    ),
                    locationDropdown = AsyncState.Success(listOf(LocationItem(name = "Gaza")))
                )
            ), editInteractions = object : IEditPostInteractions {
                override fun onTitleChange(title: String) {}
                override fun onLocationChange(location: LocationItem) {}
                override fun onDetailsChange(details: String) {}
                override fun onIsOpenChange(isOpen: Boolean) {}
                override fun onSelectedImageChange(selectedImageUri: Uri) {}
                override fun onClickSave(imageByteArray: ByteArray?) {}
                override fun onClickDelete() {}
                override fun navigateUp() {}
                override fun onRetryLocations() {}
                override fun onRetryCategories() {}
                override fun getPostDetails() {}
            },
        )
    }
}
