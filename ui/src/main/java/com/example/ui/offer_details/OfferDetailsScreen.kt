package com.example.ui.offer_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.GetFakeOffersUseCase
import com.example.domain.model.IOffer
import com.example.ui.R
import com.example.ui.components.atoms.BoxRounded
import com.example.ui.components.atoms.DetailsScreenBody
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.DetailsScreenUserHeader
import com.example.ui.components.molecules.ProductImage
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.Chip
import com.example.ui.models.OfferItemUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.IconSizeLarge
import com.example.ui.theme.IconSizeSmall
import com.example.ui.theme.Primary
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun OfferDetailsScreen(
    navController: NavController,
    viewModel: OfferDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    OfferDetailsContent(
        state = state,
        onClickGoBack = { navController.navigateUp() },
    )
}

@Composable
fun OfferDetailsContent(state: OfferItemUiState, onClickGoBack: () -> Unit) {
    TitledScreenTemplate(
        title = stringResource(R.string.offer_details),
        onClickGoBack = onClickGoBack,
        contentState = state,
    ) {
        Column {
            ProductImage(state.offerItem.imageLink)
            VerticalSpacer(Spacing16)
            DetailsScreenUserHeader(user = state.offerItem.user, date = state.offerItem.date)
            VerticalSpacer(Spacing24)
            DetailsScreenBody(state.offerItem.title, state.offerItem.details)
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

@Composable
fun PhoneRow(state: IOffer) {
    Row(
        modifier = Modifier
            .padding(horizontal = Spacing16)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = state.user.phone,
            style = TextStyles.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing16)) {
            RoundedIconButton(
                onClick = { },
                iconResId = R.drawable.ic_phone,
                contentDescription = stringResource(R.string.phone)
            )
            RoundedIconButton(
                onClick = { },
                iconResId = R.drawable.ic_chat,
                contentDescription = stringResource(R.string.what_s_up_app)
            )
            RoundedIconButton(
                onClick = { },
                iconResId = R.drawable.ic_message,
                contentDescription = stringResource(R.string.message)
            )
        }
    }
}

@Composable
fun RoundedIconButton(onClick: () -> Unit, iconResId: Int, contentDescription: String = "", modifier: Modifier = Modifier) {
    BoxRounded(
        color = Primary,
        modifier = modifier.size(IconSizeLarge).clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painterResource(iconResId),
            contentDescription = contentDescription,
            modifier = Modifier.size(IconSizeSmall),
            tint = Color.White
        )
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