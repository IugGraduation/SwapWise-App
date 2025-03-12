package com.example.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.domain.category.GetFakeCategoriesUseCase
import com.example.domain.model.PostItem
import com.example.domain.model.TopicItem
import com.example.domain.model.User
import com.example.domain.post.GetFakePostDetailsUseCase
import com.example.ui.R
import com.example.ui.add_post.navigateToAddPost
import com.example.ui.base.MyUiState
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.atoms.SwapWiseTextButton
import com.example.ui.components.atoms.SwapWiseTextField
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.PostCard
import com.example.ui.components.templates.HomeTemplate
import com.example.ui.edit_post.navigateToEditPost
import com.example.ui.models.BottomBarUiState
import com.example.ui.models.TopicsHolderUiState
import com.example.ui.post_details.navigateToPostDetails
import com.example.ui.see_all_topics.navigateToSeeAllTopics
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
    bottomNavigationViewModel: BottomNavigationViewModel = hiltViewModel()
) {
    val state by homeViewModel.state.collectAsState()
    val selectedItem by bottomNavigationViewModel.selectedItem.collectAsState()
    for (topic in state.data.topicsList) {
        topic.onClickSeeAll = { navController.navigateToSeeAllTopics(topic.url, topic.title) }
    }
    val bottomBarState = BottomBarUiState(
        selectedItem = selectedItem,
        onItemSelected = bottomNavigationViewModel::onItemSelected,
        navController = navController,
    )

    LaunchedEffect(homeViewModel.effect) {
        homeViewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffects.NavigateToAddPost -> {
                    navController.navigateToAddPost(effect.postTitle)
                }

                is HomeEffects.NavigateToPostDetails -> {
                    navController.navigateToPostDetails(effect.postId)
                }

                is HomeEffects.NavigateSeeAllTopics -> {
                    navController.navigateToSeeAllTopics(
                        title = effect.categoryTitle,
                        categoryId = effect.categoryId,
                        url = ""
                    )
                }

                is HomeEffects.NavigateToEditPost -> {
                    navController.navigateToEditPost(effect.postId)

                }
            }
        }
    }

    HomeContent(
        state = state,
        bottomBarState = bottomBarState,
        homeInteractions = homeViewModel,
    )
}


@Composable
fun HomeContent(
    state: MyUiState<HomeUiState>,
    bottomBarState: BottomBarUiState,
    homeInteractions: IHomeInteractions,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    HomeTemplate(
        state.data.user,
        bottomBarState = bottomBarState,
        baseUiState = state.baseUiState,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                SwapWiseTextField(
                    value = state.data.newPost,
                    onValueChange = homeInteractions::onNewPostFieldChange,
                    placeholder = stringResource(R.string.would_you_like_to_trade_anything),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            homeInteractions.navigateToAddPost(state.data.newPost)
                        }
                    ),
                    modifier = Modifier.padding(horizontal = Spacing16),
                )
                VerticalSpacer(Spacing24)
            }
            items(state.data.topicsList) { topic ->
                if (topic != state.data.topicsList.last()) {
                    TopicsListHeader(
                        title = topic.title,
                        onClickSeeAll = topic.onClickSeeAll
                    )
                    CustomLazyLayout(
                        items = topic.items,
                        isCategoryCard = topic.isCategoryTopics,
                        isHorizontalLayout = topic.isHorizontal,
                        onClickGoToDetails = homeInteractions::onClickGoToDetails
                    )
                    VerticalSpacer(Spacing24)
                }
            }

            //doing this because CustomLazyLayout returns a lazy column here,
            //which can't be put inside another lazy column
            val lastTopic = state.data.topicsList.lastOrNull() ?: TopicsHolderUiState()
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
                    isOpen = item.isOpen,
                    onCardClick = {
                        homeInteractions.onClickGoToDetails(item)
                    },
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
            color = MaterialTheme.color.textPrimary
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
            state = MyUiState(
                HomeUiState(
                user = user,
                topicsList = topicsList,
                )
            ),
            bottomBarState = BottomBarUiState(),
            homeInteractions = object : IHomeInteractions {
                override fun onNewPostFieldChange(newValue: String) {}
                override fun navigateToAddPost(postTitle: String) {}
                override fun onClickGoToDetails(topicItem: TopicItem) {}
            },
        )
    }
}