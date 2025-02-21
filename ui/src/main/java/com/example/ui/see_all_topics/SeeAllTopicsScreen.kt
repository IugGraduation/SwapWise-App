package com.example.ui.see_all_topics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.GetFakePostDetailsUseCase
import com.example.domain.model.CategoryItem
import com.example.domain.model.PostItem
import com.example.ui.base.MyUiState
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.models.TopicsHolderUiState
import com.example.ui.post_details.navigateToPostDetails
import com.example.ui.search.navigateToSearch
import com.example.ui.theme.GraduationProjectTheme


@Composable
fun SeeAllTopicsScreen(
    navController: NavController,
    topicSeeAllViewModel: SeeAllTopicsViewModel = hiltViewModel()
) {
    val state by topicSeeAllViewModel.state.collectAsState()

    for (item in state.data.items) {
        if (item is PostItem) {
            item.onClickGoToDetails = { navController.navigateToPostDetails(item.uuid) }
        } else if (item is CategoryItem) {
            item.onClickSearchByCategory = { navController.navigateToSearch(item.title) }
        }
    }

    SeeAllTopicsContent(
        state = state,
        onClickGoBack = { navController.navigateUp() }
    )
}


@Composable
fun SeeAllTopicsContent(state: MyUiState<TopicsHolderUiState>, onClickGoBack: () -> Unit) {

    TitledScreenTemplate(
        title = state.data.title,
        onClickGoBack = onClickGoBack,
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
        SeeAllTopicsContent(state = state, onClickGoBack = { })

    }
}