package com.example.domain

import com.example.domain.model.TopicsHolder
import kotlinx.coroutines.delay
import javax.inject.Inject

class SeeAllTopicsUseCase @Inject constructor() {
    suspend operator fun invoke(url: String): TopicsHolder {
        delay(500)

        val postItem = GetFakePostDetailsUseCase()()
        val postsList = listOf(postItem, postItem, postItem, postItem, postItem)
        return TopicsHolder(items = postsList,)
    }
}
