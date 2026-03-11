package com.sam.ui.home

import com.sam.domain.model.Home
import com.sam.domain.model.User
import com.sam.ui.models.AsyncState
import com.sam.ui.models.TopicsHolderUiState
import com.sam.ui.models.toTopicsHolderUiState


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