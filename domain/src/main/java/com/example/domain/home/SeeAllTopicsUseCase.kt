package com.example.domain.home

import com.example.data.model.response.TopicDto
import com.example.data.repository.HomeRepository
import com.example.domain.model.TopicsHolder
import com.example.domain.model.toTopicsHolder
import javax.inject.Inject

class SeeAllTopicsUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(url: String, title: String): TopicsHolder {
        return TopicDto(
                url = url,
                title = title,
                topicItemsDto = homeRepository.seeAll(url)
        ).toTopicsHolder()
    }
}
