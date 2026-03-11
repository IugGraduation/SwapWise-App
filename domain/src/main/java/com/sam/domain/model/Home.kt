package com.sam.domain.model

import com.sam.data.model.response.HomeDto

data class Home(
    val topicsList: List<TopicsHolder> = listOf(),
    val user: User = User()
)

fun HomeDto.toHome(): Home {
    val topicsList = topicDtos?.map { topicDto ->
        topicDto.toTopicsHolder()
    }
    val user = userDto.toUser()

    return Home(topicsList ?: listOf(), user)
}