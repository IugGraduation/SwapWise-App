package com.example.ui.components.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.rememberAsyncImagePainter
import com.example.domain.model.CategoryItem
import com.example.domain.model.OfferItem
import com.example.domain.model.PostItem
import com.example.domain.model.TopicItem
import com.example.ui.components.molecules.PostCard
import com.example.ui.theme.CategoryCardHorizontalHeight
import com.example.ui.theme.CategoryCardVerticalHeight
import com.example.ui.theme.CategoryCardWidth
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.PrimaryOverlay
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun CustomLazyLayout(
    items: List<TopicItem> = listOf(),
    isCategoryCard: Boolean = false,
    isHorizontalLayout: Boolean = true,
    onClickGoToDetails: (topicItem: TopicItem) -> Unit,
) {
    val card = getCard(isCategoryCard, isHorizontalLayout, onClickGoToDetails)

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
    onClickGoToDetails: (topicItem: TopicItem) -> Unit,
): @Composable (TopicItem) -> Unit {
    return when (isCategoryItem) { 
        true -> { item ->
            if (item is CategoryItem) {
                CategoryCard(
                    categoryItem = item,
                    isHorizontal = isHorizontal,
                    onClickGoShowAllCategoryPosts = onClickGoToDetails,
                )
            }
        }

        false -> { item ->
            if (item is PostItem) {
                PostCard(
                    userImage = rememberAsyncImagePainter(item.user.imageLink),
                    postImage = rememberAsyncImagePainter(item.imageLink),
                    username = item.user.name,
                    title = item.title,
                    details = item.details,
                    isOpen = item.isOpen,
                    onCardClick = { onClickGoToDetails(item) },
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
    categoryItem: CategoryItem,
    isHorizontal: Boolean = true,
    modifier: Modifier = if (isHorizontal) {
        Modifier.size(width = CategoryCardWidth, height = CategoryCardHorizontalHeight)
    } else {
        Modifier
            .fillMaxWidth()
            .height(height = CategoryCardVerticalHeight)
    },
    onClickGoShowAllCategoryPosts: (topicItem: TopicItem) -> Unit,
) {
    BoxRounded(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            painter = rememberAsyncImagePainter(categoryItem.imageLink),
            contentDescription = categoryItem.title,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxWidth(),
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(RadiusLarge))
                .background(color = PrimaryOverlay)
                .clickable { onClickGoShowAllCategoryPosts(categoryItem) },
        ) {}

        val textStyle = when (isHorizontal) {
            true -> TextStyles.smallCustomTitle
            false -> TextStyles.largeCustomTitle
        }
        CardText(text = categoryItem.title, textStyle = textStyle)
    }
}

@Composable
fun CardText(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = textStyle.copy(
            color = MaterialTheme.color.background,
            drawStyle = Stroke(width = 5F)
        ),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
    )

    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        style = textStyle.copy(
            color = MaterialTheme.color.textPrimary,
            drawStyle = Stroke(width = 1.2f, join = StrokeJoin.Round),
        ),
        textAlign = TextAlign.Center,
    )
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

@Preview
@Composable
private fun PreviewCategoryCard() {
    GraduationProjectTheme {
        CategoryCard(categoryItem = CategoryItem(title = "Category")) { }
    }
}