package com.example.ui.components.atoms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.components.molecules.CategoryCard
import com.example.ui.components.organisms.PostCard
import com.example.domain.models.CategoryItem
import com.example.ui.models.Orientation
import com.example.domain.models.PostItem
import com.example.domain.models.TopicItem
import com.example.ui.models.TopicType
import com.example.ui.models.TopicUiState
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8

@Composable
fun CustomLazyLayout(
    topic: TopicUiState,
) {
    val card = getCard(topic.type, topic.orientation)

    val content = getContent(topic.items, card)

    when (topic.orientation) {
        Orientation.Horizontal -> CustomLazyRow(content)
        Orientation.Vertical -> CustomLazyColumn(content)
    }

}


@Composable
private fun getCard(
    type: TopicType,
    orientation: Orientation
): @Composable (TopicItem) -> Unit {
    return when (type) {
        TopicType.Categories -> { item ->
            CategoryCard(
                categoryItem = item as CategoryItem,
                orientation = orientation
            )
        }
        TopicType.TopInteractive, TopicType.RecentPosts -> { item ->
            PostCard(
                postItem = item as PostItem,
                orientation = orientation
            )
        }
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