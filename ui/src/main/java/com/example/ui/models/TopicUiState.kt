package com.example.ui.models

import com.example.domain.models.TopicItem

data class TopicUiState(
    val type: TopicType = TopicType.Categories,
    val items: List<TopicItem> = listOf(),
    val orientation: Orientation = Orientation.Horizontal,
    var onClickSeeAll: () -> Unit = {},
)