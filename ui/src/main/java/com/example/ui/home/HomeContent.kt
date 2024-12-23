package com.example.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.GetCategoriesUseCase
import com.example.domain.GetPostsUseCase
import com.example.domain.GetUserUseCase
import com.example.domain.model.PostItem
import com.example.ui.R
import com.example.ui.components.atoms.CustomTextField
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.TopicsListHeader
import com.example.ui.components.organisms.CustomLazyLayoutWithHeader
import com.example.ui.components.organisms.TopicCard
import com.example.ui.components.templates.HomeTemplate
import com.example.ui.models.BottomBarUiState
import com.example.ui.models.Orientation
import com.example.ui.models.TopicType
import com.example.ui.models.TopicUiState
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8


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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                CustomTextField(
                    value = state.newPost,
                    onValueChange = onNewPostFieldChange,
                    placeholder = stringResource(R.string.would_you_like_to_trade_anything),
                    modifier = Modifier.padding(horizontal = Spacing16)
                )
                VerticalSpacer(Spacing24)
            }
            items(state.topicsList){ topic ->
                if(topic != state.topicsList.last()){
                    CustomLazyLayoutWithHeader(topic = topic)
                    VerticalSpacer(Spacing24)
                }else{
                    TopicsListHeader(topic)
                }
            }

            items(state.topicsList.last().items){
                TopicCard(
                    item = it as PostItem,
                    orientation = Orientation.Vertical,
                    modifier = Modifier.padding(horizontal = Spacing16)
                )
                VerticalSpacer(Spacing8)
            }
        }
    }
}


//@Preview(showBackground = true, showSystemUi = false,
//    device = "spec:width=1080px,height=3040px,dpi=440",
//)
@Composable
fun PreviewHomeContent() {
    val categoryItemsList = GetCategoriesUseCase()()

    val category = TopicUiState(
        type = TopicType.Categories,
        items = categoryItemsList,
    )

    val postsItemsList = GetPostsUseCase()()

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
        val user = GetUserUseCase()()
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