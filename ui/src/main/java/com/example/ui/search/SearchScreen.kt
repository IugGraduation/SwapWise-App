package com.example.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.model.PostItem
import com.example.ui.home.navigateToHome
import com.example.ui.models.BottomBarUiState
import com.example.ui.post_details.navigateToPostDetails
import com.example.ui.shared.BottomNavigationViewModel
import com.example.ui.signup.navigateToSignup
import com.example.ui.topic_see_all.navigateToTopicSeeAll

@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    mainViewModel: BottomNavigationViewModel = hiltViewModel()
) {
    val state by searchViewModel.state.collectAsState()
    val selectedItem by mainViewModel.selectedItem.collectAsState()
    for (topicItem in state.topicsList ?: listOf()) {
        topicItem.onClickGoToDetails = { navController.navigateToPostDetails(topicItem.uuid) }
    }
    val bottomBarState = BottomBarUiState(
        selectedItem = selectedItem,
        onItemSelected = mainViewModel::onItemSelected,
        navigateToHome = { navController.navigateToHome() },
        navigateToSearch = { navController.navigateToSearch() },
        navigateToNotifications = { navController.navigateToHome() },
        navigateToProfile = { navController.navigateToHome() },
    )

    SearchContent(
        state = state,
        bottomBarState = bottomBarState,
        onSearchChange = searchViewModel::onSearchChange,
        onClickTryAgain = searchViewModel::onClickTryAgain,
    )
}
