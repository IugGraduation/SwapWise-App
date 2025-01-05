package com.example.ui.post_details

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.domain.GetFakePostsUseCase
import com.example.ui.R
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.PostDetailsBody
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.DetailsPageImage
import com.example.ui.components.molecules.PostDetailsStatusRow
import com.example.ui.components.molecules.PostDetailsUserHeader
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.organisms.TopicCard
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.Chip
import com.example.ui.models.Orientation
import com.example.ui.models.PostItemUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun PostDetailsContent(
    state: PostItemUiState,
    onClickAddOffer: () -> Unit,
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
        contentState = state,
    ) {
        LazyColumn {
            item {
                DetailsPageImage(state.postItem.image)
                VerticalSpacer(Spacing16)
                PostDetailsUserHeader(user = state.postItem.user, date = state.postItem.date)
                VerticalSpacer(Spacing24)
                PostDetailsStatusRow(
                    rate = state.postItem.rate,
                    offersCount = state.postItem.offers.size,
                    isOpen = state.postItem.isOpen
                )
                VerticalSpacer(Spacing24)
                PostDetailsBody(state.postItem.title, state.postItem.details)
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
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = Spacing16)
                )
                VerticalSpacer(Spacing8)
            }
            items(state.postItem.offers) { offer ->
                TopicCard(
                    offer,
                    Orientation.Vertical,
                    modifier = Modifier
                        .padding(horizontal = Spacing16)
                        .padding(bottom = Spacing8)
                )
            }
        }

    }
}


//@Preview(showBackground = true, device = "spec:width=1080px,height=3340px,dpi=440",)
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        PostDetailsContent(
            state = PostItemUiState(postItem = GetFakePostsUseCase()()[0]),
            onClickAddOffer = {},
            onClickGoBack = {},
        )
    }
}