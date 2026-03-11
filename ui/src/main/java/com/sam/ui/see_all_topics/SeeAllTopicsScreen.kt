package com.sam.ui.see_all_topics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sam.domain.model.TopicItem
import com.sam.domain.post.GetFakePostDetailsUseCase
import com.sam.ui.base.MyUiState
import com.sam.ui.components.atoms.CustomLazyLayout
import com.sam.ui.components.templates.TitledScreenTemplate
import com.sam.ui.edit_post.navigateToEditPost
import com.sam.ui.models.TopicsHolderUiState
import com.sam.ui.post_details.navigateToPostDetails
import com.sam.ui.theme.GraduationProjectTheme


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