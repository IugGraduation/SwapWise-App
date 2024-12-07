package com.example.ui.topic_see_all

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@Composable
fun TopicSeeAllScreen(
    navController: NavController,
    topicSeeAllViewModel: TopicSeeAllViewModel = hiltViewModel()
) {
    val state by topicSeeAllViewModel.state.collectAsState()

    TopicSeeAllContent(
        topic = state,
        onClickGoBack = { navController.navigateUp() }
    )
}
