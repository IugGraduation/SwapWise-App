package com.example.ui.home

import com.example.domain.model.Home
import com.example.domain.model.User
import com.example.ui.models.TopicsHolderUiState


data class HomeUiState(
    val user: User = User(),
    val newPost: String = "",
    val topicsList: List<TopicsHolderUiState> = listOf(),
){
    companion object {
        fun fromHome(home: Home): HomeUiState {
            val topicsList = home.topicsList.map { topic ->
                TopicsHolderUiState.fromTopicsHolder(topic)
            }
            return HomeUiState(topicsList = topicsList, user = home.user)
        }
    }
}