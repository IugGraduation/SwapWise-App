package com.example.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.GetFakeCategoriesNamesUseCase
import com.example.ui.R
import com.example.ui.components.atoms.CustomLazyLayout
import com.example.ui.components.atoms.SwapWiseTextButton
import com.example.ui.components.atoms.CustomTextFieldIcon
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.components.atoms.ImageWithMaxWidth
import com.example.ui.components.atoms.SwapWiseTextField
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.MainTitledScreenTemplate
import com.example.ui.home.navigateToHome
import com.example.ui.models.BottomBarUiState
import com.example.ui.models.Chip
import com.example.ui.notifications.navigateToNotifications
import com.example.ui.post_details.navigateToPostDetails
import com.example.ui.profile.navigateToProfile
import com.example.ui.shared.BottomNavigationViewModel
import com.example.ui.theme.BlackFourth
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles

@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    bottomNavigationViewModel: BottomNavigationViewModel = hiltViewModel()
) {
    val state by searchViewModel.state.collectAsState()
    val selectedItem by bottomNavigationViewModel.selectedItem.collectAsState()
    for (topicItem in state.topicsList) {
        topicItem.onClickGoToDetails = { navController.navigateToPostDetails(topicItem.uuid) }
    }
    val bottomBarState = BottomBarUiState(
        selectedItem = selectedItem,
        onItemSelected = bottomNavigationViewModel::onItemSelected,
        navigateToHome = { navController.navigateToHome() },
        navigateToSearch = { navController.navigateToSearch() },
        navigateToNotifications = { navController.navigateToNotifications() },
        navigateToProfile = { navController.navigateToProfile() },
    )

    SearchContent(
        state = state,
        bottomBarState = bottomBarState,
        searchInteractions = searchViewModel
    )
}


@Composable
fun SearchContent(
    state: SearchUiState,
    bottomBarState: BottomBarUiState,
    searchInteractions: ISearchInteractions,
) {
    MainTitledScreenTemplate(
        title = stringResource(R.string.search),
        bottomBarState = bottomBarState,
    ) {
        SwapWiseTextField(
            value = state.search,
            onValueChange = searchInteractions::onSearchChange,
            placeholder = stringResource(R.string.search),
            leadingIcon = {
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing8)) {
                    CustomTextFieldIcon(
                        painter = painterResource(R.drawable.ic_search)
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
        TitledChipsList(chipsList = state.filterChipsList)
        VerticalSpacer(Spacing16)
        if (state.baseUiState.errorMessage.isNotEmpty()) {
            EmptyContent(searchInteractions::onClickTryAgain)
        } else if (state.baseUiState.isLoading) {
            LoadingContent()
        } else {
            CustomLazyLayout(
                items = state.topicsList,
                isCategoryCard = false,
                isHorizontalLayout = false
            )
        }
    }
}

@Composable
private fun EmptyContent(onClickTryAgain: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight(0.8f)
    ) {
        ImageWithMaxWidth(
            painterResource(R.drawable.ic_empty_box),
            contentDescription = stringResource(R.string.searching)
        )
        VerticalSpacer(Spacing8)
        Row(
            horizontalArrangement = Arrangement.Center,
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
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight(0.8f)
    ) {
        ImageWithMaxWidth(
            painterResource(R.drawable.ic_searching),
            contentDescription = stringResource(R.string.searching)
        )
    }
}


//@Preview(showBackground = true)
@Composable
fun PreviewAllTopicsContent() {
    GraduationProjectTheme {
        val searchUiState = SearchUiState(
//            topicsList = GetFakePostsUseCase()(),
            filterChipsList = GetFakeCategoriesNamesUseCase()().map {
                Chip(text = it, onClick = { }, selected = false)
            },
//            isLoading = true,
        )
        SearchContent(
            state = searchUiState,
            bottomBarState = BottomBarUiState(selectedItem = 1),
            searchInteractions = object : ISearchInteractions {
                override fun onSearchChange(newValue: String) {}
                override fun onClickTryAgain() {}
            }
        )
    }
}