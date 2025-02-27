package com.example.domain.see_all_topics

import com.example.domain.model.TopicsHolder
import com.example.domain.post.GetFakePostDetailsUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

class SeeAllTopicsUseCase @Inject constructor() {
    suspend operator fun invoke(url: String): TopicsHolder {
        delay(500)

        val postItem = GetFakePostDetailsUseCase()()
        val postsList = listOf(postItem, postItem, postItem, postItem, postItem)
        return TopicsHolder(title = "Top Interactive", items = postsList,)
    }
}
