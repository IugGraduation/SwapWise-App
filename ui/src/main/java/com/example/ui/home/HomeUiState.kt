package com.example.ui.home

import com.example.domain.model.Home
import com.example.ui.models.TopicUiState
import com.example.domain.model.User
import com.example.domain.model.UiState
import com.example.ui.base.BaseUiState


data class HomeUiState(
    val user: User = User(),
    val newPost: String = "",
    val topicsList: List<TopicUiState> = listOf(),
    val baseUiState: BaseUiState = BaseUiState()
){
    companion object {
        fun fromHome(home: Home): HomeUiState {
            val topicsList = home.topicsList.map { topic ->
                TopicUiState.fromTopic(topic)
            }
            return HomeUiState(topicsList = topicsList, user = home.user)
        }
    }
}