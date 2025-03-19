package com.example.ui.post_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.domain.model.OfferItem
import com.example.domain.post.GetFakePostDetailsUseCase
import com.example.ui.R
import com.example.ui.add_offer.navigateToAddOffer
import com.example.ui.base.MyUiState
import com.example.ui.components.atoms.DetailsScreenBody
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.DetailsScreenUserHeader
import com.example.ui.components.molecules.PostCard
import com.example.ui.components.molecules.ProductImage
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.edit_offer.navigateToEditOffer
import com.example.ui.edit_post.navigateToEditPost
import com.example.ui.models.ChipUiState
import com.example.ui.models.PostItemUiState
import com.example.ui.offer_details.navigateToOfferDetails
import com.example.ui.profile.composable.EditIconButton
import com.example.ui.theme.BlackFourth
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8
import com.example.ui.theme.Spacing80
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun PostDetailsScreen(
    navController: NavController,
    viewModel: PostDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onResume()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PostDetailsEffects.NavigateToAddOffer -> {
                    navController.navigateToAddOffer(state.data.postItem.uuid)
                }

                is PostDetailsEffects.NavigateToOfferDetails -> {
                    navController.navigateToOfferDetails(effect.offerId)
                }

                is PostDetailsEffects.NavigateToEditOffer -> {
                    navController.navigateToEditOffer(effect.offerId)
                }

                is PostDetailsEffects.NavigateToEditPost -> {
                    navController.navigateToEditPost(effect.postId)
                }

                is PostDetailsEffects.NavigateUp -> navController.navigateUp()
            }
        }
    }
    PostDetailsContent(
        state = state,
        postDetailsInteractions = viewModel,
    )
}

@Composable
fun PostDetailsContent(
    state: MyUiState<PostItemUiState>,
    postDetailsInteractions: PostDetailsInteractions,
) {
    TitledScreenTemplate(
        title = stringResource(R.string.post_details),
        onClickGoBack = postDetailsInteractions::navigateUp,
        floatingActionButton = {
            AnimatedVisibility(!state.data.showEditPostButton) {
                SwapWiseFilledButton(
                    onClick = postDetailsInteractions::navigateToAddOffer,
                    text = stringResource(R.string.add_offer),
                    modifier = Modifier.padding(horizontal = Spacing16)
                )
            }
        },
        actions = {
            AnimatedVisibility(state.data.showEditPostButton) {
                EditIconButton { postDetailsInteractions.navigateToEditPost(state.data.postItem.uuid) }
            }
        },
        baseUiState = state.baseUiState,
    ) {
        LazyColumn {
            item {
                ProductImage(state.data.postItem.imageLink)
                VerticalSpacer(Spacing16)
                DetailsScreenUserHeader(
                    user = state.data.postItem.user,
                    date = state.data.postItem.date
                )
                VerticalSpacer(Spacing24)
                StatusRow(
                    rate = state.data.postItem.rate,
                    offersCount = state.data.postItem.offers.size,
                    isOpen = state.data.postItem.isOpen
                )
                VerticalSpacer(Spacing24)
                DetailsScreenBody(state.data.postItem.title, state.data.postItem.details)
                VerticalSpacer(Spacing24)
                TitledChipsList(
                    title = stringResource(R.string.favorite_categories),
                    chipsList = state.data.postItem.favoriteCategoryItems.map {
                        ChipUiState(
                            categoryItem = it,
                            selected = mutableStateOf(
                                state.data.postItem.favoriteCategoryItems.contains(
                                    it
                                )
                            ),
                            clickable = false
                        )
                    }
                )
                VerticalSpacer(Spacing24)
                Text(
                    text = stringResource(R.string.offers),
                    style = TextStyles.headingMedium,
                    color = MaterialTheme.color.textPrimary,
                    modifier = Modifier.padding(horizontal = Spacing16)
                )
                VerticalSpacer(Spacing8)
            }

            items(state.data.postItem.offers.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing16)
                        .padding(bottom = Spacing8),
                    horizontalArrangement = Arrangement.spacedBy(Spacing16)
                ) {
                    rowItems.forEach { offerItem ->
                        Box(modifier = Modifier.weight(1f)) {
                            PostCard(
                                username = offerItem.user.name,
                                userImage = rememberAsyncImagePainter(offerItem.user.imageLink),
                                title = offerItem.title,
                                isPostCard = false,
                                details = offerItem.details,
                                postImage = rememberAsyncImagePainter(offerItem.imageLink),
                                onCardClick = {
                                    postDetailsInteractions.navigateToOfferDetails(offerItem)
                                }
                            )
                        }
                    }
                    // In case the last row has only one item, add a spacer to balance the row.
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            item {
                VerticalSpacer(Spacing80) //space for floating button at the bottom
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
            color = MaterialTheme.color.textPrimary
        )
        VerticalSpacer(Spacing4)
        Text(
            title,
            style = TextStyles.captionLarge,
            color = MaterialTheme.color.textTertiary
        )
    }
}


@Preview(showBackground = true, device = "spec:width=1080px,height=3340px,dpi=440")
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        PostDetailsContent(
            state = MyUiState(PostItemUiState(postItem = GetFakePostDetailsUseCase()())),
            postDetailsInteractions = object : PostDetailsInteractions {
                override fun navigateToAddOffer() {}
                override fun navigateToOfferDetails(offerItem: OfferItem) {}
                override fun navigateToEditPost(postId: String) {}
                override fun navigateUp() {}
            },
        )
    }
}