package com.example.ui.topic_see_all

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.ui.R
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.domain.model.CategoryItem
import com.example.ui.models.Orientation
import com.example.ui.models.TopicType
import com.example.ui.models.TopicUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.util.getName

@Composable
fun TopicSeeAllContent(state: TopicUiState, onClickGoBack: () -> Unit) {
    val title =
        if (state.type == TopicType.Categories) stringResource(R.string.all) + " " + state.type.getName()
        else state.type.getName()

    TitledScreenTemplate(
        title = title,
        onClickGoBack = onClickGoBack,
        contentState = state,
    ) {
        CustomLazyLayout(items = state.items,
            isCategoryCard = state.type == TopicType.Categories,
            isHorizontalLayout = false)
    }
}


//@Preview(showBackground = true)
@Composable
fun PreviewAllTopicsContent() {
    GraduationProjectTheme {
        val categoryItem = CategoryItem(
            stringResource(R.string.food_and_beverages),
            R.drawable.img_food_and_beverages.toString(),
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
        TopicSeeAllContent(state = category, onClickGoBack = { })

    }
}