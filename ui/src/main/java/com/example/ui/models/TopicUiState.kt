package com.example.ui.models

import com.example.domain.model.Topic
import com.example.domain.model.TopicItem

data class TopicUiState(
    val type: TopicType = TopicType.Categories,
    val items: List<TopicItem> = listOf(),
    val orientation: Orientation = Orientation.Horizontal,
    var onClickSeeAll: () -> Unit = {},
    val url: String = "",
){
    companion object{
        fun fromTopic(topic: Topic): TopicUiState {
            val type = TopicType.valueOf(topic.title.replace(" ", ""))
            return TopicUiState(
                type = type,
                items = topic.items,
                url = topic.url
//                orientation = "",
            )
        }
    }
}