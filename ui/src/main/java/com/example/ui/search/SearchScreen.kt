package com.example.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ui.home.navigateToHome
import com.example.ui.models.BottomBarUiState
import com.example.ui.notifications.navigateToNotifications
import com.example.ui.post_details.navigateToPostDetails
import com.example.ui.profile.navigateToProfile
import com.example.ui.shared.BottomNavigationViewModel

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
        navigateToNotifications = { navController.navigateToNotifications() },
        navigateToProfile = { navController.navigateToProfile() },
    )

    SearchContent(
        state = state,
        bottomBarState = bottomBarState,
        onSearchChange = searchViewModel::onSearchChange,
        onClickTryAgain = searchViewModel::onClickTryAgain,
    )
}
