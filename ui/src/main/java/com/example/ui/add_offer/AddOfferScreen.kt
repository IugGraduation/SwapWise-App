package com.example.ui.add_offer

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.model.CategoryItem
import com.example.domain.model.OfferItem
import com.example.ui.R
import com.example.ui.add_post.IAddPostInteractions
import com.example.ui.base.MyUiState
import com.example.ui.base.NavigateUpEffect
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.SwapWiseTextField
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.ProductImage
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.ChipUiState
import com.example.ui.models.OfferItemUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun AddOfferScreen(navController: NavController, viewModel: AddOfferViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NavigateUpEffect.NavigateUp -> navController.navigateUp()
            }
        }
    }

    AddOfferContent(
        state = state,
        addInteractions = viewModel,
    )
}


@Composable
fun AddOfferContent(
    state: MyUiState<OfferItemUiState>,
    addInteractions: IAddPostInteractions,
) {
    TitledScreenTemplate(
        title = stringResource(R.string.add_offer),
        onClickGoBack = addInteractions::navigateUp,
        baseUiState = state.baseUiState,
    ) {
        ProductImage(
            state.data.offerItem.imageLink,
            onImagePicked = addInteractions::onSelectedImageChange
        )
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(Spacing16),
            verticalArrangement = Arrangement.spacedBy(Spacing8)
        ) {
            Text(
                stringResource(R.string.offer_info),
                style = TextStyles.headingLarge,
                color = MaterialTheme.color.textPrimary
            )

            val focusManager = LocalFocusManager.current

            SwapWiseTextField(
                value = state.data.offerItem.title,
                onValueChange = addInteractions::onTitleChange,
                placeholder = stringResource(R.string.offer_title),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_title),
                        contentDescription = stringResource(R.string.offer_title),
                        tint = MaterialTheme.color.textTertiary
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                errorMessage = state.data.offerError.titleError,
            )
            SwapWiseTextField(
                value = state.data.offerItem.place,
                onValueChange = addInteractions::onPlaceChange,
                placeholder = stringResource(R.string.your_place),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = stringResource(R.string.your_place),
                        tint = MaterialTheme.color.textTertiary
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                errorMessage = state.data.offerError.placeError,
            )
            SwapWiseTextField(
                value = state.data.offerItem.details,
                onValueChange = addInteractions::onDetailsChange,
                placeholder = stringResource(R.string.details),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_details),
                        contentDescription = stringResource(R.string.details),
                        tint = MaterialTheme.color.textTertiary
                    )
                },
                isMultiline = true,
                errorMessage = state.data.offerError.detailsError,
            )
            VerticalSpacer(Spacing16)
            TitledChipsList(
                title = stringResource(R.string.category_of_the_offer),
                textStyle = TextStyles.headingLarge,
                chipsList = state.data.chipsList.onEach {
                    it.selected.value = it.categoryItem == state.data.offerItem.categoryItem
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
                    onClick = addInteractions::onClickAdd,
                    text = stringResource(R.string.add_offer),
                    modifier = Modifier.padding(horizontal = Spacing16)
                )
            }
        }
    }
}


@Preview(showBackground = true, device = "spec:width=1080px,height=2540px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        AddOfferContent(
            state = MyUiState(
                OfferItemUiState(
                    offerItem = OfferItem(categoryItem = CategoryItem("Category1")),
                    chipsList = listOf(
                        ChipUiState(categoryItem = CategoryItem("Category1")),
                        ChipUiState(categoryItem = CategoryItem("Category2")),
                        ChipUiState(categoryItem = CategoryItem("Category3"))
                    )
                )
            ),
            addInteractions = object : IAddPostInteractions {
                override fun onTitleChange(title: String) {}
                override fun onPlaceChange(place: String) {}
                override fun onDetailsChange(details: String) {}
                override fun onSelectedImageChange(selectedImageUri: Uri) {}
                override fun onClickAdd(imageByteArray: ByteArray?) {}
                override fun navigateUp() {}
            },
        )
    }
}