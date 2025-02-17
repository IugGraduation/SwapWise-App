package com.example.ui.post_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.domain.GetFakePostDetailsUseCase
import com.example.ui.R
import com.example.ui.add_offer.navigateToAddOffer
import com.example.ui.components.atoms.DetailsScreenBody
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.DetailsScreenUserHeader
import com.example.ui.components.molecules.PostCard
import com.example.ui.components.molecules.ProductImage
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.Chip
import com.example.ui.models.PostItemUiState
import com.example.ui.offer_details.navigateToOfferDetails
import com.example.ui.theme.BlackFourth
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun PostDetailsScreen(
    navController: NavController,
    viewModel: PostDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    PostDetailsContent(
        state = state,
        onClickAddOffer = { navController.navigateToAddOffer(state.postItem.uuid) },
        onClickOfferCard = navController::navigateToOfferDetails,
        onClickGoBack = navController::navigateUp,
    )
}


@Composable
fun PostDetailsContent(
    state: PostItemUiState,
    onClickAddOffer: () -> Unit,
    onClickOfferCard: (postId: String) -> Unit,
    onClickGoBack: () -> Unit
) {
    TitledScreenTemplate(
        title = stringResource(R.string.post_details),
        onClickGoBack = onClickGoBack,
        floatingActionButton = {
            SwapWiseFilledButton(
                onClick = onClickAddOffer,
                text = stringResource(R.string.add_offer),
                modifier = Modifier.padding(horizontal = Spacing16)
            )
        },
        baseUiState = state.baseUiState,
    ) {
        LazyColumn {
            item {
                ProductImage(state.postItem.imageLink)
                VerticalSpacer(Spacing16)
                DetailsScreenUserHeader(user = state.postItem.user, date = state.postItem.date)
                VerticalSpacer(Spacing24)
                StatusRow(
                    rate = state.postItem.rate,
                    offersCount = state.postItem.offers.size,
                    isOpen = state.postItem.isOpen
                )
                VerticalSpacer(Spacing24)
                DetailsScreenBody(state.postItem.title, state.postItem.details)
                VerticalSpacer(Spacing24)
                TitledChipsList(
                    title = stringResource(R.string.favorite_categories),
                    chipsList = state.postItem.favoriteCategories.map {
                        Chip(
                            text = it,
                            selected = state.postItem.favoriteCategories.contains(it),
                            onClick = {})
                    }
                )
                VerticalSpacer(Spacing24)
                Text(
                    text = stringResource(R.string.offers),
                    style = TextStyles.headingMedium,
                    color = MaterialTheme.color.primary,
                    modifier = Modifier.padding(horizontal = Spacing16)
                )
                VerticalSpacer(Spacing8)
            }
            items(state.postItem.offers) { offer ->
                PostCard(
                    username = offer.user.name,
                    userImage = rememberAsyncImagePainter(offer.user.imageLink),
                    title = offer.title,
                    isPostCard = false,
                    details = offer.details,
                    postImage = rememberAsyncImagePainter(offer.imageLink),
                    onCardClick = { onClickOfferCard(offer.uuid) }
                )
            }
        }
    }
}

@Composable
fun StatusRow(rate: Float, offersCount: Int, isOpen: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PostDetailsStatusItem(title = stringResource(R.string.rate), value = rate.toString())
        HorizontalSpacer(Spacing24)
        Spacer(
            modifier = Modifier
                .size(width = 1.5.dp, height = 20.dp)
                .background(color = BlackFourth, shape = RoundedCornerShape(RadiusLarge))
        )
        HorizontalSpacer(Spacing24)
        PostDetailsStatusItem(
            title = stringResource(R.string.offers),
            value = offersCount.toString()
        )
        HorizontalSpacer(Spacing24)
        Spacer(
            modifier = Modifier
                .size(width = 1.5.dp, height = 20.dp)
                .background(color = BlackFourth, shape = RoundedCornerShape(RadiusLarge))
        )
        HorizontalSpacer(Spacing24)
        val state = if (isOpen) stringResource(R.string.open) else stringResource(R.string.closed)
        PostDetailsStatusItem(title = stringResource(R.string.state), value = state)
    }
}

@Composable
fun PostDetailsStatusItem(title: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            style = TextStyles.headingSmall,
            color = MaterialTheme.color.primary
        )
        VerticalSpacer(Spacing4)
        Text(
            title,
            style = TextStyles.captionLarge,
            color = MaterialTheme.color.tertiary
        )
    }
}


@Preview(showBackground = true, device = "spec:width=1080px,height=3340px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        PostDetailsContent(
            state = PostItemUiState(postItem = GetFakePostDetailsUseCase()()),
            onClickAddOffer = {},
            onClickOfferCard = {},
            onClickGoBack = {},
        )
    }
}