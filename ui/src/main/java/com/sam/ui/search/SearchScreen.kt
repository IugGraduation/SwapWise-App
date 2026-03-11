package com.sam.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sam.domain.category.GetFakeCategoriesUseCase
import com.sam.domain.model.CategoryItem
import com.sam.ui.R
import com.sam.ui.base.MyUiState
import com.sam.ui.components.AsyncContent
import com.sam.ui.components.atoms.CustomLazyLayout
import com.sam.ui.components.atoms.HorizontalSpacer
import com.sam.ui.components.atoms.SwapWiseTextButton
import com.sam.ui.components.atoms.SwapWiseTextField
import com.sam.ui.components.atoms.VerticalSpacer
import com.sam.ui.components.molecules.TitledChipsList
import com.sam.ui.components.templates.MainTitledScreenTemplate
import com.sam.ui.edit_post.navigateToEditPost
import com.sam.ui.models.AsyncState
import com.sam.ui.models.BottomBarUiState
import com.sam.ui.models.ChipUiState
import com.sam.ui.post_details.navigateToPostDetails
import com.sam.ui.shared.BottomNavigationViewModel
import com.sam.ui.theme.BlackFourth
import com.sam.ui.theme.GraduationProjectTheme
import com.sam.ui.theme.Spacing16
import com.sam.ui.theme.Spacing24
import com.sam.ui.theme.Spacing4
import com.sam.ui.theme.Spacing8
import com.sam.ui.theme.TextStyles
import com.sam.ui.theme.color

@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    bottomNavigationViewModel: BottomNavigationViewModel = hiltViewModel()
) {
    val state by searchViewModel.state.collectAsState()
    val selectedItem by bottomNavigationViewModel.selectedItem.collectAsState()

    val bottomBarState = BottomBarUiState(
        selectedItem = selectedItem,
        onItemSelected = bottomNavigationViewModel::onItemSelected,
        navController = navController
    )

    LaunchedEffect(searchViewModel.effect) {
        searchViewModel.effect.collect { effect ->
            when (effect) {
                is SearchEffects.NavigateToPostDetails -> navController.navigateToPostDetails(effect.postId)
                is SearchEffects.NavigateToEditPost -> navController.navigateToEditPost(effect.postId)
            }
        }
    }

    SearchContent(
        state = state,
        bottomBarState = bottomBarState,
        searchInteractions = searchViewModel
    )
}


@Composable
fun SearchContent(
    state: MyUiState<SearchUiState>,
    bottomBarState: BottomBarUiState,
    searchInteractions: ISearchInteractions,
) {
    MainTitledScreenTemplate(
        title = stringResource(R.string.search),
        bottomBarState = bottomBarState,
    ) {
        SwapWiseTextField(
            value = state.data.search,
            onValueChange = searchInteractions::onSearchChange,
            placeholder = stringResource(R.string.search),
            leadingIcon = {
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing8)) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = stringResource(R.string.search),
                        tint = MaterialTheme.color.textTertiary
                    )
                    Spacer(
                        Modifier
                            .height(Spacing24)
                            .width(1.5.dp)
                            .background(color = BlackFourth)
                    )
                }
            },
            modifier = Modifier.padding(horizontal = Spacing16)
        )
        VerticalSpacer(Spacing8)
        TitledChipsList(
            title = stringResource(id = R.string.categories),
            state = state.data.categoriesFilter,
            onRetry = searchInteractions::onRetryCategories
        )
        VerticalSpacer(Spacing8)
        TitledChipsList(
            title = stringResource(id = R.string.location),
            state = state.data.locationsFilter,
            onRetry = searchInteractions::onRetryLocations
        )
        VerticalSpacer(Spacing16)

        AsyncContent(
            state = state.data.topicsList,
            onRetry = searchInteractions::onClickTryAgain,
            initialContent = { InitialSearchContent() },
            loadingContent = { LoadingContent() },
            emptyContent = { EmptyContent(searchInteractions::onClickTryAgain) }
        ) { posts ->
            CustomLazyLayout(
                items = posts,
                isHorizontalLayout = false,
                onClickGoToDetails = { item -> searchInteractions.navigateToPostDetails(item.id) }
            )
        }
    }
}

@Composable
private fun InitialSearchContent() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.color.textTertiary.copy(alpha = 0.3f)
        )
        VerticalSpacer(Spacing8)
        Text(
            text = stringResource(R.string.start_searching_now),
            style = TextStyles.hint,
            color = MaterialTheme.color.textTertiary
        )
    }
}

@Composable
private fun EmptyContent(onClickTryAgain: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_box),
            contentDescription = stringResource(R.string.searching),
            modifier = Modifier.fillMaxWidth(),
        )
        VerticalSpacer(Spacing8)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.nothing_found), style = TextStyles.hint)
            HorizontalSpacer(Spacing4)
            SwapWiseTextButton(onClick = onClickTryAgain, text = stringResource(R.string.try_again))
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_searching),
            contentDescription = stringResource(R.string.searching),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSearchContent() {
    GraduationProjectTheme {
        val searchUiState = SearchUiState(
            categoriesFilter = AsyncState.Success(
                GetFakeCategoriesUseCase()().map {
                    ChipUiState(categoryItem = CategoryItem(name = it.name, id = it.id))
                }
            ),
            locationsFilter = AsyncState.Success(
                listOf(
                    ChipUiState(CategoryItem(name = "Gaza")),
                    ChipUiState(CategoryItem(name = "London"))
                )
            )
        )
        SearchContent(
            state = MyUiState(searchUiState),
            bottomBarState = BottomBarUiState(selectedItem = 1),
            searchInteractions = object : ISearchInteractions {
                override fun onSearchChange(newValue: String) {}
                override fun onClickTryAgain() {}
                override fun navigateToPostDetails(postId: String) {}
                override fun onRetryCategories() {}
                override fun onRetryLocations() {}
            }
        )
    }
}
