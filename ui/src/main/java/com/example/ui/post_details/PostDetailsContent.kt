package com.example.ui.post_details

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.domain.GetPostsUseCase
import com.example.domain.model.PostItem
import com.example.ui.R
import com.example.ui.components.atoms.CustomButton
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
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun PostDetailsContent(state: PostItem, onClickAddOffer: () -> Unit, onClickGoBack: () -> Unit) {
    TitledScreenTemplate(
        title = stringResource(R.string.post_details),
        onClickGoBack = onClickGoBack,
        floatingActionButton = {
            CustomButton(
                onClick = onClickAddOffer,
                text = stringResource(R.string.add_offer),
                modifier = Modifier.padding(horizontal = Spacing16)
            )
        }
    ) {
        LazyColumn {
            item {
                //todo: view image correctly here
                DetailsPageImage(null)
                VerticalSpacer(Spacing16)
                PostDetailsUserHeader(user = state.user, date = state.date)
                VerticalSpacer(Spacing24)
                PostDetailsStatusRow(
                    rate = state.rate,
                    offersCount = state.offers.size,
                    isOpen = state.isOpen
                )
                VerticalSpacer(Spacing24)
                PostDetailsBody(state.title, state.details)
                VerticalSpacer(Spacing24)
                TitledChipsList(
                    title = stringResource(R.string.favorite_categories),
                    chipsList = Chip.fromCategories(state.favoriteCategories, true)
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
            items(state.offers) { offer ->
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
            state = GetPostsUseCase()()[0],
            onClickAddOffer = {},
            onClickGoBack = {},
        )
    }
}