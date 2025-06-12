package com.example.domain.model

import com.example.data.model.response.HomeDto

data class Home(
    val topicsList: List<TopicsHolder> = listOf(),
    val user: User = User()
) {
    companion object {
        fun fromHomeDto(homeDto: HomeDto): Home {
            val topicsList = homeDto.topicsData?.map { topicDto ->
                topicDto.toTopicsHolder()
            }
            val user = User.fromUserDto(homeDto.user)
            return Home(topicsList ?: listOf(), user)
        }
    }
}

fun HomeDto.toHome(): Home {
    val topicsList = topicsData?.map { topicDto ->
        topicDto.toTopicsHolder()
    }
    val user = User.fromUserDto(user)
    return Home(topicsList ?: listOf(), user)
}