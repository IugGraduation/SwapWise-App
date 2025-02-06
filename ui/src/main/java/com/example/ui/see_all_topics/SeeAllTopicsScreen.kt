package com.example.ui.see_all_topics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.model.CategoryItem
import com.example.ui.R
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.TopicsHolderUiState
import com.example.ui.theme.GraduationProjectTheme


@Composable
fun SeeAllTopicsScreen(
    navController: NavController,
    topicSeeAllViewModel: SeeAllTopicsViewModel = hiltViewModel()
) {
    val state by topicSeeAllViewModel.state.collectAsState()

    SeeAllTopicsContent(
        state = state,
        onClickGoBack = { navController.navigateUp() }
    )
}


@Composable
fun SeeAllTopicsContent(state: TopicsHolderUiState, onClickGoBack: () -> Unit) {

    TitledScreenTemplate(
        title = state.title,
        onClickGoBack = onClickGoBack,
        baseUiState = state.baseUiState,
    ) {
        CustomLazyLayout(items = state.items,
            isCategoryCard = state.isCategoryTopics,
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

        val category = TopicsHolderUiState(
            title = "Categories",
            items = categoryItemsList,
            isHorizontal = true,
            onClickSeeAll = { },
        )
        SeeAllTopicsContent(state = category, onClickGoBack = { })

    }
}