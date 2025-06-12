package com.example.domain.model

import com.example.data.model.response.HomeDto

data class Home(
    val topicsList: List<TopicsHolder> = listOf(),
    val user: User = User()
)

fun HomeDto.toHome(): Home {
    val topicsList = topicsData?.map { topicDto ->
        topicDto.toTopicsHolder()
    }
    val user = userDto.toUser()

    return Home(topicsList ?: listOf(), user)
}