package com.example.ui.models

import com.example.domain.model.TopicItem
import com.example.domain.model.TopicsHolder

data class TopicsHolderUiState(
    val title: String = "Categories",
    val items: List<TopicItem> = listOf(),
    val isHorizontal: Boolean = true,
    var onClickSeeAll: () -> Unit = {},
    val url: String = "Categories",
)

fun TopicsHolder.toTopicsHolderUiState(): TopicsHolderUiState {
    return TopicsHolderUiState(
        title = title,
        items = items,
        url = url,
    )
}