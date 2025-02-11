package com.example.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.domain.GetFakeCategoriesUseCase
import com.example.domain.GetFakePostDetailsUseCase
import com.example.domain.model.PostItem
import com.example.domain.model.User
import com.example.ui.R
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.atoms.SwapWiseTextButton
import com.example.ui.components.atoms.SwapWiseTextField
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.PostCard
import com.example.ui.components.templates.HomeTemplate
import com.example.ui.models.BottomBarUiState
import com.example.ui.models.TopicsHolderUiState
import com.example.ui.notifications.navigateToNotifications
import com.example.ui.post_details.navigateToPostDetails
import com.example.ui.profile.navigateToProfile
import com.example.ui.search.navigateToSearch
import com.example.ui.see_all_topics.navigateToTopicSeeAll
import com.example.ui.shared.BottomNavigationViewModel
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: BottomNavigationViewModel = hiltViewModel()
) {
    val state by homeViewModel.state.collectAsState()
    val selectedItem by mainViewModel.selectedItem.collectAsState()
    for (topic in state.topicsList) {
        topic.onClickSeeAll = { navController.navigateToTopicSeeAll(topic.url) }
        for (item in topic.items) {
            if (item is PostItem) {
                item.onClickGoToDetails = { navController.navigateToPostDetails(item.uuid) }
            }
        }
    }
    val bottomBarState = BottomBarUiState(
        selectedItem = selectedItem,
        onItemSelected = mainViewModel::onItemSelected,
        navigateToHome = { navController.navigateToHome() },
        navigateToSearch = { navController.navigateToSearch() },
        navigateToNotifications = { navController.navigateToNotifications() },
        navigateToProfile = { navController.navigateToProfile() },
    )

    HomeContent(
        state = state,
        bottomBarState = bottomBarState,
        homeInteractions = homeViewModel
    )
}


@Composable
fun HomeContent(
    state: HomeUiState,
    bottomBarState: BottomBarUiState,
    homeInteractions: IHomeInteractions
) {
    HomeTemplate(
        state.user,
        bottomBarState = bottomBarState,
        baseUiState = state.baseUiState,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                SwapWiseTextField(
                    value = state.newPost,
                    onValueChange = homeInteractions::onNewPostFieldChange,
                    placeholder = stringResource(R.string.would_you_like_to_trade_anything),
                    modifier = Modifier.padding(horizontal = Spacing16)
                    //todo: on click enter, go to add post screen
                )
                VerticalSpacer(Spacing24)
            }
            items(state.topicsList) { topic ->
                if (topic != state.topicsList.last()) {
                    TopicsListHeader(
                        title = topic.title,
                        onClickSeeAll = topic.onClickSeeAll
                    )
                    CustomLazyLayout(
                        items = topic.items,
                        isCategoryCard = topic.isCategoryTopics,
                        isHorizontalLayout = topic.isHorizontal,
                    )
                    VerticalSpacer(Spacing24)
                }
            }

            //doing this because CustomLazyLayout returns a lazy column here,
            //which can't be put inside another lazy column
            val lastTopic = state.topicsList.lastOrNull() ?: TopicsHolderUiState()
            item {
                TopicsListHeader(
                    title = lastTopic.title,
                    onClickSeeAll = lastTopic.onClickSeeAll
                )
            }
            items(lastTopic.items) { item ->
                PostCard(
                    userImage = rememberAsyncImagePainter((item as PostItem).user.imageLink),
                    postImage = rememberAsyncImagePainter(item.imageLink),
                    username = item.user.name,
                    title = item.title,
                    details = item.details,
                    onCardClick = { item.onClickGoToDetails() },
                    isHorizontalCard = false,
                    modifier = Modifier.padding(horizontal = Spacing16)
                )
                VerticalSpacer(Spacing8)
            }
        }
    }
}

@Composable
fun TopicsListHeader(
    title: String,
    onClickSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = Spacing16)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = TextStyles.headingMedium,
            color = MaterialTheme.color.primary
        )
        SwapWiseTextButton(
            onClick = onClickSeeAll,
            text = stringResource(R.string.see_all),
        )
    }
    VerticalSpacer(Spacing8)
}


@Preview(
    showBackground = true, showSystemUi = false,
    device = "spec:width=1080px,height=3040px,dpi=440",
)
@Composable
fun PreviewHomeContent() {
    val categoryItemsList = GetFakeCategoriesUseCase()()

    val category = TopicsHolderUiState(
        title = "Categories",
        items = categoryItemsList,
    )

    val postsItemsList = listOf(
        GetFakePostDetailsUseCase()(),
        GetFakePostDetailsUseCase()(),
        GetFakePostDetailsUseCase()(),
        GetFakePostDetailsUseCase()(),
        GetFakePostDetailsUseCase()()
    )

    val topInteractive = TopicsHolderUiState(
        title = "TopInteractive",
        items = postsItemsList,
        isHorizontal = true,
    )
    val recentPosts = TopicsHolderUiState(
        title = "RecentPosts",
        items = postsItemsList,
        isHorizontal = false,
    )
    val topicsList = listOf(category, topInteractive, recentPosts)

    GraduationProjectTheme {
        val user = User(
            name = "Cameron Williamson",
            phone = "1231231231"
        )
        HomeContent(
            state = HomeUiState(
                user = user,
                topicsList = topicsList,
            ),
            bottomBarState = BottomBarUiState(),
            homeInteractions = object : IHomeInteractions {
                override fun onNewPostFieldChange(newValue: String) {}
            },
        )
    }
}