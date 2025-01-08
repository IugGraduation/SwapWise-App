package com.example.ui.offer_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.domain.GetFakeOffersUseCase
import com.example.ui.R
import com.example.ui.components.atoms.PostDetailsBody
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.ProductImage
import com.example.ui.components.molecules.PhoneRow
import com.example.ui.components.molecules.PostDetailsUserHeader
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.Chip
import com.example.ui.models.OfferItemUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun OfferDetailsContent(state: OfferItemUiState, onClickGoBack: () -> Unit) {
    TitledScreenTemplate(
        title = stringResource(R.string.offer_details),
        onClickGoBack = onClickGoBack,
        contentState = state,
    ) {
        Column {
            ProductImage(state.offerItem.image)
            VerticalSpacer(Spacing16)
            PostDetailsUserHeader(user = state.offerItem.user, date = state.offerItem.date)
            VerticalSpacer(Spacing24)
            PostDetailsBody(state.offerItem.title, state.offerItem.details)
            VerticalSpacer(Spacing24)
            TitledChipsList(
                title = stringResource(R.string.category_of_the_offer),
                chipsList = listOf(Chip(text = state.offerItem.category, selected = true, clickable = false))
            )
            VerticalSpacer(Spacing24)
            Text(
                text = stringResource(R.string.phone),
                style = TextStyles.headingMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = Spacing16)
            )
            VerticalSpacer(Spacing8)
            PhoneRow(state.offerItem)
        }

    }
}


//@Preview(showBackground = true, device = "spec:width=1080px,height=3340px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        OfferDetailsContent(
            state = OfferItemUiState(offerItem = GetFakeOffersUseCase()()[0]),
            onClickGoBack = {},
        )
    }
}