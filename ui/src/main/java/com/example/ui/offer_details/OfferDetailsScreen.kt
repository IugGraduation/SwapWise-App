package com.example.ui.offer_details

import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.GetFakeOfferDetailsUseCase
import com.example.ui.R
import com.example.ui.base.MyUiState
import com.example.ui.components.atoms.BoxRounded
import com.example.ui.components.atoms.DetailsScreenBody
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.DetailsScreenUserHeader
import com.example.ui.components.molecules.ProductImage
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.ChipUiState
import com.example.ui.models.OfferItemUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.IconSizeLarge
import com.example.ui.theme.IconSizeSmall
import com.example.ui.theme.Primary
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun OfferDetailsScreen(
    navController: NavController,
    viewModel: OfferDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val phone = state.data.offerItem.user.phone

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OfferDetailsEffects.NavigateToPhone -> {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$phone")
                    }
                    context.startActivity(intent)
                }

                OfferDetailsEffects.NavigateToWhatsapp -> {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://wa.me/$phone")
                    }
                    context.startActivity(intent)
                }

                OfferDetailsEffects.NavigateToMessages -> {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("sms:$phone")
                    }
                    context.startActivity(intent)
                }

                OfferDetailsEffects.NavigateUp -> navController.navigateUp()
            }
        }
    }

    OfferDetailsContent(
        state = state,
        offerDetailsInteractions = viewModel
    )
}

@Composable
fun OfferDetailsContent(
    state: MyUiState<OfferItemUiState>,
    offerDetailsInteractions: OfferDetailsInteractions,
) {
    TitledScreenTemplate(
        title = stringResource(R.string.offer_details),
        onClickGoBack = offerDetailsInteractions::navigateUp,
        baseUiState = state.baseUiState,
    ) {
        Column {
            ProductImage(state.data.offerItem.imageLink)
            VerticalSpacer(Spacing16)
            DetailsScreenUserHeader(
                user = state.data.offerItem.user,
                date = state.data.offerItem.date
            )
            VerticalSpacer(Spacing24)
            DetailsScreenBody(state.data.offerItem.title, state.data.offerItem.details)
            VerticalSpacer(Spacing24)
            TitledChipsList(
                title = stringResource(R.string.category_of_the_offer),
                chipsList = listOf(
                    ChipUiState(
                        text = state.data.offerItem.category,
                        selected = true,
                        clickable = false
                    )
                )
            )
            VerticalSpacer(Spacing24)
            Text(
                text = stringResource(R.string.phone),
                style = TextStyles.headingMedium,
                color = MaterialTheme.color.textPrimary,
                modifier = Modifier.padding(horizontal = Spacing16)
            )
            VerticalSpacer(Spacing8)
            PhoneRow(
                phone = state.data.offerItem.user.phone,
                onClickPhoneButton = offerDetailsInteractions::navigateToPhone,
                onClickWhatsappButton = offerDetailsInteractions::navigateToWhatsapp,
                onClickMessageButton = offerDetailsInteractions::navigateToMessages
            )
        }

    }
}

@Composable
fun PhoneRow(
    phone: String,
    onClickPhoneButton: () -> Unit,
    onClickWhatsappButton: () -> Unit,
    onClickMessageButton: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = Spacing16)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = phone,
            style = TextStyles.bodyLarge,
            color = MaterialTheme.color.textTertiary
        )
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing16)) {
            RoundedIconButton(
                onClick = onClickPhoneButton,
                iconResId = R.drawable.ic_phone,
                contentDescription = stringResource(R.string.phone)
            )
            RoundedIconButton(
                onClick = onClickWhatsappButton,
                iconResId = R.drawable.ic_chat,
                contentDescription = stringResource(R.string.what_s_up_app)
            )
            RoundedIconButton(
                onClick = onClickMessageButton,
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
        modifier = modifier
            .size(IconSizeLarge)
            .clickable { onClick() },
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


@Preview(showBackground = true, device = "spec:width=1080px,height=3340px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        OfferDetailsContent(
            state = MyUiState(OfferItemUiState(offerItem = GetFakeOfferDetailsUseCase()())),
            offerDetailsInteractions = object : OfferDetailsInteractions {
                override fun navigateToPhone() {}
                override fun navigateToWhatsapp() {}
                override fun navigateToMessages() {}
                override fun navigateUp() {}
            },
        )
    }
}