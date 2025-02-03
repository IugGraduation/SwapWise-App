package com.example.ui.add_offer

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.domain.model.OfferItem
import com.example.ui.R
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.CustomTextFieldIcon
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.ProductImage
import com.example.ui.components.molecules.SimpleCustomMultilineTextField
import com.example.ui.components.molecules.SimpleCustomTextField
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.OfferItemUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun AddOfferContent(
    state: OfferItemUiState,
    onTitleChange: (String) -> Unit,
    onPlaceChange: (String) -> Unit,
    onDetailsChange: (String) -> Unit,
    onSelectedImageChange: (Uri) -> Unit,
    onClickAddOffer: () -> Unit,
    onClickGoBack: () -> Unit
) {
    TitledScreenTemplate(
        title = stringResource(R.string.add_offer),
        onClickGoBack = onClickGoBack,
        floatingActionButton = {
            SwapWiseFilledButton(
                onClick = onClickAddOffer,
                text = stringResource(R.string.add_offer),
                modifier = Modifier.padding(horizontal = Spacing16)
            )
        },
        contentState = state
    ) {
        ProductImage(state.offerItem.imageLink, onImagePicked = onSelectedImageChange)
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
                value = state.offerItem.title,
                onValueChange = onTitleChange,
                placeholder = stringResource(R.string.offer_title),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_title)
                    )
                },
                errorMessage = state.offerItem.titleError,
            )
            VerticalSpacer(Spacing8)
//            SimpleCustomTextField(
//                value = state.offerItem.phone,
//                onValueChange = onPhoneChange,
//                placeholder = stringResource(R.string.phone_number),
//                leadingIcon = {
//                    CustomTextFieldIcon(
//                        painter = painterResource(R.drawable.ic_phone)
//                    )
//                },
//                errorMessage = state.offerItem.phoneError,
//            )
//            VerticalSpacer(Spacing8)
            SimpleCustomTextField(
                value = state.offerItem.place,
                onValueChange = onPlaceChange,
                placeholder = stringResource(R.string.your_place),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_location)
                    )
                },
                errorMessage = state.offerItem.placeError,
            )
            VerticalSpacer(Spacing8)
            SimpleCustomMultilineTextField(
                value = state.offerItem.details,
                onValueChange = onDetailsChange,
                placeholder = stringResource(R.string.details),
                leadingIcon = {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_details)
                    )
                },
                errorMessage = state.offerItem.detailsError,
            )
            VerticalSpacer(Spacing24)
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


//@Preview(showBackground = true, device = "spec:width=1080px,height=2540px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        AddOfferContent(
            state = OfferItemUiState(offerItem = OfferItem(category = "Category")),
            onClickAddOffer = { },
            onClickGoBack = { },
            onTitleChange = { },
            onPlaceChange = { },
            onDetailsChange = { },
            onSelectedImageChange = { },
        )
    }
}