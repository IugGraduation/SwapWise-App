package com.example.ui.topic_see_all

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.R
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.templates.TopicSeeAllTemplate
import com.example.domain.models.CategoryItem
import com.example.ui.models.Orientation
import com.example.ui.models.TopicType
import com.example.ui.models.TopicUiState
import com.example.ui.models.getName
import com.example.ui.theme.GraduationProjectTheme

@Composable
fun TopicSeeAllContent(topic: TopicUiState, onClickGoBack: () -> Unit) {
    val myTopic = topic.copy(orientation = Orientation.Vertical)
    val title =
        if (topic.type == TopicType.Categories) stringResource(R.string.all) + " " + topic.type.getName()
        else topic.type.getName()

    TopicSeeAllTemplate(
        title = title,
        onClickGoBack = onClickGoBack
    ) {
        CustomLazyLayout(topic = myTopic)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAllTopicsContent() {
    GraduationProjectTheme {
        val categoryItem = CategoryItem(
            stringResource(R.string.food_and_beverages),
            R.drawable.img_food_and_beverages,
            ""
        )
        val categoryItemsList =
            listOf(categoryItem, categoryItem, categoryItem, categoryItem, categoryItem)

        val category = TopicUiState(
            type = TopicType.Categories,
            items = categoryItemsList,
            orientation = Orientation.Horizontal,
            onClickSeeAll = { },
        )
        TopicSeeAllContent(topic = category, onClickGoBack = { })

    }
}