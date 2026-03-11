package com.sam.ui.home

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
import androidx.compose.runtime.DisposableEffect
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.sam.domain.category.GetFakeCategoriesUseCase
import com.sam.domain.model.PostItem
import com.sam.domain.model.TopicItem
import com.sam.domain.model.User
import com.sam.domain.post.GetFakePostDetailsUseCase
import com.sam.ui.R
import com.sam.ui.add_post.navigateToAddPost
import com.sam.ui.base.MyUiState
import com.sam.ui.components.AsyncContent
import com.sam.ui.components.atoms.CustomLazyLayout
import com.sam.ui.components.atoms.SwapWiseTextButton
import com.sam.ui.components.atoms.SwapWiseTextField
import com.sam.ui.components.atoms.VerticalSpacer
import com.sam.ui.components.molecules.PostCard
import com.sam.ui.components.templates.HomeTemplate
import com.sam.ui.edit_post.navigateToEditPost
import com.sam.ui.home.composable.AddIconButton
import com.sam.ui.models.AsyncState
import com.sam.ui.models.BottomBarUiState
import com.sam.ui.models.TopicsHolderUiState
import com.sam.ui.post_details.navigateToPostDetails
import com.sam.ui.see_all_topics.navigateToSeeAllTopics
import com.sam.ui.shared.BottomNavigationViewModel
import com.sam.ui.theme.GraduationProjectTheme
import com.sam.ui.theme.Spacing16
import com.sam.ui.theme.Spacing24
import com.sam.ui.theme.Spacing8
import com.sam.ui.theme.TextStyles
import com.sam.ui.theme.color

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    bottomNavigationViewModel: BottomNavigationViewModel = hiltViewModel()
) {
    val state by homeViewModel.state.collectAsState()
    val selectedItem by bottomNavigationViewModel.selectedItem.collectAsState()
    for (topic in state.data.homeData.data?.topicsList ?: emptyList()) {
        topic.onClickSeeAll = { navController.navigateToSeeAllTopics(topic.url, topic.title) }
    }
    val bottomBarState = BottomBarUiState(
        selectedItem = selectedItem,
        onItemSelected = bottomNavigationViewModel::onItemSelected,
        navController = navController,
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                homeViewModel.onResume()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

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
        user = state.data.homeData.data?.user ?: User(),
        bottomBarState = bottomBarState,
        floatingActionButton = {
            AddIconButton { homeInteractions.navigateToAddPost() }
        },
        baseUiState = state.baseUiState,
    ) {
        AsyncContent(
            state = state.data.homeData,
            onRetry = homeInteractions::onRetryHome
        ) { homeData ->
            val topics = homeData.topicsList

            LazyColumn(
                modifier = Modifier.fillMaxSize()
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

                items(topics.dropLast(1)) { topic ->
                    TopicsListHeader(
                        title = topic.title,
                        onClickSeeAll = topic.onClickSeeAll
                    )
                    CustomLazyLayout(
                        items = topic.items,
                        isHorizontalLayout = topic.isHorizontal,
                        onClickGoToDetails = homeInteractions::onClickGoToDetails
                    )
                    VerticalSpacer(Spacing24)
                }

                val lastTopic = topics.lastOrNull()
                if (lastTopic != null) {
                    item {
                        TopicsListHeader(
                            title = lastTopic.title,
                            onClickSeeAll = lastTopic.onClickSeeAll
                        )
                    }
                    items(lastTopic.items) { item ->
                        PostCard(
                            userImage = rememberAsyncImagePainter((item as PostItem).user.imageLink),
                            postImage = rememberAsyncImagePainter(item.imageUrl),
                            username = item.user.name,
                            title = item.name,
                            details = item.details,
                            location = item.locationItem.name,
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
    device = "spec:width=1080px,height=3040px,dpi=440", apiLevel = 34,
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
                    homeData = AsyncState.Success(HomeDataUI(user = user, topicsList = topicsList))
                )
            ),
            bottomBarState = BottomBarUiState(),
            homeInteractions = object : IHomeInteractions {
                override fun onNewPostFieldChange(newValue: String) {}
                override fun navigateToAddPost(postTitle: String) {}
                override fun onClickGoToDetails(topicItem: TopicItem) {}
                override fun onRetryHome() {}
            },
        )
    }
}
