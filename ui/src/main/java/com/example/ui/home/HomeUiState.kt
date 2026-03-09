package com.example.ui.home

import com.example.domain.model.Home
import com.example.domain.model.User
import com.example.ui.models.AsyncState
import com.example.ui.models.TopicsHolderUiState
import com.example.ui.models.toTopicsHolderUiState


data class HomeUiState(
    val homeData: AsyncState<HomeDataUI> = AsyncState.Initial,
    val newPost: String = "",
)

data class HomeDataUI(
    val user: User = User(),
    val topicsList: List<TopicsHolderUiState> = listOf(),
)

fun Home.toHomeDataUI(): HomeDataUI {
    val topicsList = topicsList.map { topic ->
        topic.toTopicsHolderUiState()
    }
    return HomeDataUI(topicsList = topicsList, user = user)
}