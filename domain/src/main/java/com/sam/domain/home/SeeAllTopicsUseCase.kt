package com.sam.domain.home

import com.sam.data.model.response.TopicDto
import com.sam.data.repository.HomeRepository
import com.sam.domain.model.TopicsHolder
import com.sam.domain.model.toTopicsHolder
import javax.inject.Inject

class SeeAllTopicsUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(url: String, title: String): TopicsHolder {
        return TopicDto(
                url = url,
                title = title,
                topicItemDtos = homeRepository.seeAll(url)
        ).toTopicsHolder()
    }
}
