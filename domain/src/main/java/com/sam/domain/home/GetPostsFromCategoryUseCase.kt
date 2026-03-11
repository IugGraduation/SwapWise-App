package com.sam.domain.home

import com.sam.data.model.response.TopicDto
import com.sam.data.repository.HomeRepository
import com.sam.domain.model.TopicsHolder
import com.sam.domain.model.toTopicsHolder
import javax.inject.Inject

class GetPostsFromCategoryUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(categoryId: String, title: String): TopicsHolder {
        return TopicDto(
            title = title,
            topicItemDtos = homeRepository.getPostsFromCategory(categoryId)
        ).toTopicsHolder()
    }
}
