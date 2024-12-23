package com.example.domain.model

import com.example.data.model.HomeDto


data class Home(
    val topicsList: List<Topic>,
    val user: User
) {
    companion object {
        fun fromHomeDto(homeDto: HomeDto): Home {
            val topicsList = homeDto.topicsData?.map { topicDto ->
                Topic.fromTopicDto(topicDto)
            }
            val user = User.fromUserDto(homeDto.user)
            return Home(topicsList?: listOf(), user)
        }
    }
}
