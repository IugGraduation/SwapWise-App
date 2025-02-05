package com.example.ui.components.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.domain.model.OfferItem
import com.example.domain.model.PostItem
import com.example.domain.model.TopicItem
import com.example.ui.components.molecules.PostCard
import com.example.ui.theme.PrimaryOverlay
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun CustomLazyLayout(
    items: List<TopicItem> = listOf(),
    isCategoryCard: Boolean = false,
    isHorizontalLayout: Boolean = true,
) {
    val card = getCard(isCategoryCard, isHorizontalLayout)

    val content = getContent(items, card)

    when (isHorizontalLayout) {
        true -> CustomLazyRow(content)
        false -> CustomLazyColumn(content)
    }
}

@Composable
private fun getCard(
    isCategoryItem: Boolean = false,
    isHorizontal: Boolean = true,
): @Composable (TopicItem) -> Unit {
    return when (isCategoryItem) {
        true -> { item ->
            CategoryCard(
                categoryImage = rememberAsyncImagePainter(item.imageLink),
                title = item.title,
                isHorizontal = isHorizontal,
            )
        }

        false -> { item ->
            if (item is PostItem) {
                PostCard(
                    userImage = rememberAsyncImagePainter(item.user.imageLink),
                    postImage = rememberAsyncImagePainter(item.imageLink),
                    username = item.user.name,
                    title = item.title,
                    details = item.details,
                    onCardClick = { item.onClickGoToDetails() },
                    isHorizontalCard = isHorizontal,
                )
            } else if (item is OfferItem) {
                PostCard(
                    userImage = rememberAsyncImagePainter(item.user.imageLink),
                    postImage = rememberAsyncImagePainter(item.imageLink),
                    username = item.user.name,
                    title = item.title,
                    details = item.details,
                    isPostCard = false,
                    isHorizontalCard = isHorizontal,
                    onCardClick = { },
                )
            }
        }
    }
}


@Composable
fun CategoryCard(
    categoryImage: Painter,
    title: String,
    modifier: Modifier = Modifier,
    isHorizontal: Boolean = true,
) {
    val myModifier = when (isHorizontal) {
        true -> modifier.size(width = 108.dp, height = 80.dp)
        false -> modifier
            .fillMaxWidth()
            .height(height = 164.dp)
    }

    BoxRounded(modifier = myModifier, contentAlignment = Alignment.Center) {
        Image(
            painter = categoryImage,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxWidth(),
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(RadiusLarge))
                .background(color = PrimaryOverlay),
        ) {}

        val textStyle = when (isHorizontal) {
            true -> TextStyles.smallCustomTitle
            false -> TextStyles.largeCustomTitle
        }
        CardText(text = title, textStyle = textStyle)
    }
}

@Composable
private fun getContent(
    items: List<TopicItem>,
    card: @Composable (TopicItem) -> Unit
): LazyListScope.() -> Unit = {
    items(items) { item ->
        card(item)
    }
}

@Composable
private fun CustomLazyRow(content: LazyListScope.() -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = Spacing16),
        horizontalArrangement = Arrangement.spacedBy(Spacing8)
    ) {
        content()
    }
}

@Composable
private fun CustomLazyColumn(content: LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing16),
        verticalArrangement = Arrangement.spacedBy(Spacing8)
    ) {
        content()
    }
}