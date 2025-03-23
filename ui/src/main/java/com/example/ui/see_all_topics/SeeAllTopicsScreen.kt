package com.example.ui.see_all_topics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.model.TopicItem
import com.example.domain.post.GetFakePostDetailsUseCase
import com.example.ui.base.MyUiState
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.edit_post.navigateToEditPost
import com.example.ui.models.TopicsHolderUiState
import com.example.ui.post_details.navigateToPostDetails
import com.example.ui.theme.GraduationProjectTheme


@Composable
fun SeeAllTopicsScreen(
    navController: NavController,
    seeAllTopicsViewModel: SeeAllTopicsViewModel = hiltViewModel(),
) {
    val state by seeAllTopicsViewModel.state.collectAsState()

    LaunchedEffect(seeAllTopicsViewModel.effect) {
        seeAllTopicsViewModel.effect.collect { effect ->
            when (effect) {
                is SeeAllTopicsEffects.NavigateToPostDetails -> navController.navigateToPostDetails(
                    effect.postId
                )

                is SeeAllTopicsEffects.NavigateSeeAllTopics -> {
                    navController.navigateToSeeAllTopics(
                        title = effect.categoryTitle,
                        categoryId = effect.categoryId,
                        url = ""
                    )
                }

                is SeeAllTopicsEffects.NavigateToEditPost -> navController.navigateToEditPost(
                    effect.postId
                )

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
    seeAllTopicsInteractions: ISeeAllInteractions
) {

    TitledScreenTemplate(
        title = state.data.title,
        onClickGoBack = seeAllTopicsInteractions::navigateUp,
        baseUiState = state.baseUiState,
    ) {
        CustomLazyLayout(
            items = state.data.items,
            isCategoryCard = state.data.isCategoryTopics,
            isHorizontalLayout = false,
            onClickGoToDetails = seeAllTopicsInteractions::onClickGoToDetails
        )
    }
}


@Preview(showBackground = true)
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
        SeeAllTopicsContent(state = state, seeAllTopicsInteractions = object : ISeeAllInteractions {
            override fun onClickGoToDetails(topicItem: TopicItem) {}
            override fun navigateUp() {}
        })

    }
}