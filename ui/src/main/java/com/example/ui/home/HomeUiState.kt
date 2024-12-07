package com.example.ui.home

import com.example.ui.models.TopicUiState
import com.example.domain.models.User


data class HomeUiState(
    val user: User = User(),
    val newPost: String = "",
    val topicsList: List<TopicUiState> = listOf(),
)