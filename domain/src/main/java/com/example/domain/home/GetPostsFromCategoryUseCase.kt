package com.example.domain.home

import com.example.data.model.response.TopicDto
import com.example.data.repository.HomeRepository
import com.example.domain.model.TopicsHolder
import javax.inject.Inject

class GetPostsFromCategoryUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(categoryId: String, title: String): TopicsHolder {
        return TopicsHolder.fromTopicDto(
            TopicDto(
                title = title,
                topicItemsDto = homeRepository.getPostsFromCategory(categoryId)
            )
        )
    }
}
