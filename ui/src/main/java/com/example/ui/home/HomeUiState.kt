package com.example.ui.home

import com.example.domain.model.Home
import com.example.domain.model.User
import com.example.ui.models.TopicsHolderUiState
import com.example.ui.models.toTopicsHolderUiState


data class HomeUiState(
    val user: User = User(),
    val newPost: String = "",
    val topicsList: List<TopicsHolderUiState> = listOf(),
)

fun Home.toHomeUiState(): HomeUiState {
    val topicsList = topicsList.map { topic ->
        topic.toTopicsHolderUiState()
    }
    return HomeUiState(topicsList = topicsList, user = user)
}