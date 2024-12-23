package com.example.domain.model

import com.example.data.model.TopicDto


data class Topic(
    val title: String = "",
    val items: List<TopicItem> = listOf(),
    val url: String = ""
){
    companion object{
        fun fromTopicDto(topicDto: TopicDto): Topic{
            val items = topicDto.topicItemsDto?.map { topicItemDto ->
                if(topicDto.title == "Categories"){
                    CategoryItem.fromTopicItemDto(topicItemDto)
                }else{
                    PostItem.fromTopicItemDto(topicItemDto)
                }

            }
            return Topic(
                title = topicDto.title.toString(),
                items = items ?: listOf(),
                url = topicDto.url.toString()
            )
        }
    }
}