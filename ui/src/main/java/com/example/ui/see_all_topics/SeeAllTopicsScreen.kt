package com.example.ui.see_all_topics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.GetFakePostDetailsUseCase
import com.example.domain.model.CategoryItem
import com.example.domain.model.PostItem
import com.example.ui.base.INavigateUp
import com.example.ui.base.MyUiState
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.TopicsHolderUiState
import com.example.ui.post_details.navigateToPostDetails
import com.example.ui.search.navigateToSearch
import com.example.ui.shared.BottomNavigationViewModel
import com.example.ui.theme.GraduationProjectTheme


@Composable
fun SeeAllTopicsScreen(
    navController: NavController,
    seeAllTopicsViewModel: SeeAllTopicsViewModel = hiltViewModel(),
    bottomNavigationViewModel: BottomNavigationViewModel = hiltViewModel()
) {
    val state by seeAllTopicsViewModel.state.collectAsState()

    for (item in state.data.items) {
        if (item is PostItem) {
            item.onClickGoToDetails = { seeAllTopicsViewModel.navigateToPostDetails(item.uuid) }
        } else if (item is CategoryItem) {
            item.onClickSearchByCategory = { seeAllTopicsViewModel.navigateToSearch(item.title) }
        }
    }

    LaunchedEffect(seeAllTopicsViewModel.effect) {
        seeAllTopicsViewModel.effect.collect { effect ->
            when (effect) {
                is SeeAllTopicsEffects.NavigateToPostDetails -> navController.navigateToPostDetails(
                    effect.postId
                )

                is SeeAllTopicsEffects.NavigateToSearch -> {
                    bottomNavigationViewModel.onItemSelected(1)
                    navController.navigateToSearch(effect.filterCategoryName)
                }

                SeeAllTopicsEffects.NavigateUp -> navController.navigateUp()
            }
        }
    }

    SeeAllTopicsContent(
        state = state,
        seeAllTopicsInteractions = seeAllTopicsViewModel
    )
}


@Composable
fun SeeAllTopicsContent(
    state: MyUiState<TopicsHolderUiState>,
    seeAllTopicsInteractions: INavigateUp
) {

    TitledScreenTemplate(
        title = state.data.title,
        onClickGoBack = seeAllTopicsInteractions::navigateUp,
        baseUiState = state.baseUiState,
    ) {
        CustomLazyLayout(
            items = state.data.items,
            isCategoryCard = state.data.isCategoryTopics,
            isHorizontalLayout = false)
    }
}


//@Preview(showBackground = true)
@Composable
fun PreviewAllTopicsContent() {
    GraduationProjectTheme {
        val postItem = GetFakePostDetailsUseCase()()
        val postsList = listOf(postItem, postItem, postItem, postItem, postItem)
        val state = MyUiState(
            TopicsHolderUiState(
            title = "Top Interactive",
            items = postsList,
            isHorizontal = false
            )
        )
        SeeAllTopicsContent(state = state, seeAllTopicsInteractions = object : INavigateUp {
            override fun navigateUp() {}
        })

    }
}