package com.example.ui.components.atoms

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.domain.model.CategoryItem
import com.example.domain.model.PostItem
import com.example.domain.model.TopicItem
import com.example.ui.components.molecules.CategoryCard
import com.example.ui.components.organisms.TopicCard
import com.example.ui.models.Orientation
import com.example.ui.models.TopicType
import com.example.ui.models.TopicUiState

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
            TopicCard(
                item = item as PostItem,
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
