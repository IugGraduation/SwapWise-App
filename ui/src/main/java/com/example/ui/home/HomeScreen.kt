package com.example.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.models.PostItem
import com.example.ui.models.BottomBarUiState
import com.example.ui.post_details.navigateToPostDetails
import com.example.ui.shared.MainViewModel
import com.example.ui.signup.navigateToSignup
import com.example.ui.topic_see_all.navigateToTopicSeeAll

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val state by homeViewModel.state.collectAsState()
    val selectedItem by mainViewModel.selectedItem.collectAsState()
    for(topic in state.topicsList){
        topic.onClickSeeAll = {navController.navigateToTopicSeeAll(topic.type.name)}
        for(item in topic.items){
            if(item is PostItem){
                item.onClickGoToDetails = {navController.navigateToPostDetails(item.title)}
            }
        }
    }
    val bottomBarState = BottomBarUiState(
        selectedItem = selectedItem,
        onItemSelected = mainViewModel::onItemSelected,
        navigateToHome = { navController.navigateToHome() },
        navigateToSearch = { navController.navigateToSignup() },
        navigateToNotifications = { navController.navigateToHome() },
        navigateToProfile = { navController.navigateToHome() },
    )

    HomeContent(
        state = state,
        bottomBarState = bottomBarState,
        onNewPostFieldChange = homeViewModel::onNewPostFieldChange
    )
}

