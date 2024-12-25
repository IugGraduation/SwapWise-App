package com.example.ui.edit_offer

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
import com.example.domain.GetOffersUseCase
import com.example.domain.model.OfferItem
import com.example.ui.R
import com.example.ui.components.atoms.CustomButton
import com.example.ui.components.atoms.CustomOutlinedButton
import com.example.ui.components.atoms.CustomTextFieldIcon
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.DetailsPageImage
import com.example.ui.components.molecules.SimpleCustomMultilineTextField
import com.example.ui.components.molecules.SimpleCustomTextField
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.Chip
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun EditOfferContent(
    state: OfferItem,
    onTitleChange: (String) -> Unit,
    onPlaceChange: (String) -> Unit,
    onDetailsChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onClickSave: () -> Unit,
    onClickDelete: () -> Unit,
    onClickAddImage: () -> Unit,
    onClickGoBack: () -> Unit
) {
    TitledScreenTemplate(
        title = stringResource(R.string.edit_offer),
        onClickGoBack = onClickGoBack,
        floatingActionButton = {
            Column {
                CustomButton(
                    onClick = onClickSave,
                    text = stringResource(R.string.save),
                    modifier = Modifier.padding(horizontal = Spacing16)
                )
                VerticalSpacer(Spacing8)
                CustomOutlinedButton(
                    onClick = onClickDelete,
                    text = stringResource(R.string.delete),
                    modifier = Modifier.padding(horizontal = Spacing16)
                )
            }
        },
        contentState = state
    ) {
        DetailsPageImage(state.image, onClick = onClickAddImage)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing16)
        ) {
            Text(
                stringResource(R.string.offer_info),
                style = TextStyles.headingLarge,
                color = MaterialTheme.colorScheme.primary
            )
            VerticalSpacer(Spacing8)
            SimpleCustomTextField(
                value = state.title,
                onValueChange = onTitleChange,
                placeholder = stringResource(R.string.offer_title),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_title)
                    )
                },
                errorMessage = state.titleError,
            )
            VerticalSpacer(Spacing8)

            SimpleCustomTextField(
                value = state.place,
                onValueChange = onPlaceChange,
                placeholder = stringResource(R.string.your_place),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_location)
                    )
                },
                errorMessage = state.placeError,
            )
            VerticalSpacer(Spacing8)
            SimpleCustomMultilineTextField(
                value = state.details,
                onValueChange = onDetailsChange,
                placeholder = stringResource(R.string.details),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_details)
                    )
                },
                errorMessage = state.detailsError,
            )
            VerticalSpacer(Spacing24)
            TitledChipsList(
                title = stringResource(R.string.category_of_the_offer),
                textStyle = TextStyles.headingLarge,
                chipsList = state.allCategories.map {
                    Chip(
                        text = it,
                        onClick = onCategoryChange,
                        selected = it == state.category
                    )
                },
            )

        }

    }
}


//@Preview(showBackground = true, device = "spec:width=1080px,height=2540px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        EditOfferContent(
            state = GetOffersUseCase()()[0].copy(
                allCategories = GetCategoriesNamesUseCase(
                    GetCategoriesUseCase()
                ).getFakeData(),
                category = "Food and beverages0",
            ),
            onClickSave = { },
            onClickDelete = { },
            onClickGoBack = { },
            onTitleChange = { },
            onPlaceChange = { },
            onDetailsChange = { },
            onClickAddImage = { },
            onCategoryChange = { },
        )
    }
}