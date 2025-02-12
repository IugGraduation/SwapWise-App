package com.example.ui.add_offer

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.model.OfferItem
import com.example.ui.R
import com.example.ui.add_post.IAddPostInteractions
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.SwapWiseTextField
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.ProductImage
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.Chip
import com.example.ui.models.OfferItemUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun AddOfferScreen(navController: NavController, viewModel: AddOfferViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.baseUiState.shouldNavigateUp) {
        if (state.baseUiState.shouldNavigateUp) navController.navigateUp()
    }

    AddOfferContent(
        state = state,
        addInteractions = viewModel,
        onClickGoBack = { navController.navigateUp() },
    )
}


@Composable
fun AddOfferContent(
    state: OfferItemUiState,
    addInteractions: IAddPostInteractions,
    onClickGoBack: () -> Unit
) {
    TitledScreenTemplate(
        title = stringResource(R.string.add_offer),
        onClickGoBack = onClickGoBack,
        floatingActionButton = {
            SwapWiseFilledButton(
                onClick = addInteractions::onClickAdd,
                text = stringResource(R.string.add_offer),
                modifier = Modifier.padding(horizontal = Spacing16)
            )
        },
        baseUiState = state.baseUiState,
    ) {
        ProductImage(
            state.offerItem.imageLink,
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
                color = MaterialTheme.colorScheme.primary
            )
            SwapWiseTextField(
                value = state.offerItem.title,
                onValueChange = addInteractions::onTitleChange,
                placeholder = stringResource(R.string.offer_title),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_title),
                        contentDescription = stringResource(R.string.offer_title),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                },
                errorMessage = state.offerError.titleError,
            )
            SwapWiseTextField(
                value = state.offerItem.place,
                onValueChange = addInteractions::onPlaceChange,
                placeholder = stringResource(R.string.your_place),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = stringResource(R.string.your_place),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                },
                errorMessage = state.offerError.placeError,
            )
            SwapWiseTextField(
                value = state.offerItem.details,
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
                errorMessage = state.offerError.detailsError,
            )
            VerticalSpacer(Spacing16)
            TitledChipsList(
                title = stringResource(R.string.category_of_the_offer),
                textStyle = TextStyles.headingLarge,
                chipsList = state.chipsList.onEach {
                    it.selected = it.text == state.offerItem.category
                },
            )
        }
    }
}


@Preview(showBackground = true, device = "spec:width=1080px,height=2540px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        AddOfferContent(
            state = OfferItemUiState(
                offerItem = OfferItem(category = "Category"), chipsList = listOf(
                    Chip(text = "Category"), Chip(text = "Category2"), Chip(text = "Category3")
                )
            ),
            onClickGoBack = { },
            addInteractions = object : IAddPostInteractions {
                override fun onTitleChange(title: String) {}
                override fun onPlaceChange(place: String) {}
                override fun onDetailsChange(details: String) {}
                override fun onSelectedImageChange(selectedImageUri: Uri) {}
                override fun onClickAdd() {}
            },
        )
    }
}