package com.example.ui.home

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.GetCategoriesUseCase
import com.example.domain.GetPostsUseCase
import com.example.domain.GetUserUseCase
import com.example.ui.R
import com.example.ui.components.atoms.CustomTextField
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.organisms.CustomLazyLayoutWithHeader
import com.example.ui.components.templates.HomeTemplate
import com.example.ui.models.BottomBarUiState
import com.example.ui.models.Orientation
import com.example.ui.models.TopicType
import com.example.ui.models.TopicUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24


@Composable
fun HomeContent(
    state: HomeUiState,
    bottomBarState: BottomBarUiState,
    onNewPostFieldChange: (String) -> Unit,
) {
    HomeTemplate(
        state.user,
        bottomBarState = bottomBarState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()//.verticalScroll(rememberScrollState())
        ) {
            CustomTextField(
                value = state.newPost,
                onValueChange = onNewPostFieldChange,
                placeholder = stringResource(R.string.would_you_like_to_trade_anything),
                modifier = Modifier.padding(horizontal = Spacing16)
            )
            VerticalSpacer(Spacing24)
            for (topic in state.topicsList) {
                CustomLazyLayoutWithHeader(topic = topic)
                VerticalSpacer(Spacing24)
            }


        }
    }
}


@Preview(showBackground = true, showSystemUi = false)
@Composable
fun PreviewHomeContent() {
    val categoryItemsList = GetCategoriesUseCase().invoke()

    val category = TopicUiState(
        type = TopicType.Categories,
        items = categoryItemsList,
    )

    val postsItemsList = GetPostsUseCase().invoke()

    val topInteractive = TopicUiState(
        type = TopicType.TopInteractive,
        items = postsItemsList,
    )
    val recentPosts = TopicUiState(
        type = TopicType.RecentPosts,
        items = postsItemsList,
        orientation = Orientation.Vertical,
    )
    val topicsList = listOf(category, topInteractive, recentPosts)

    GraduationProjectTheme {
        val user = GetUserUseCase().invoke()
        HomeContent(
            state = HomeUiState(
                user = user,
                topicsList = topicsList,
            ),
            bottomBarState = BottomBarUiState(),
            onNewPostFieldChange = { },
        )
    }
}