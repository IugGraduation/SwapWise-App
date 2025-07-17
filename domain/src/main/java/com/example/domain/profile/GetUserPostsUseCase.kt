package com.example.domain.profile

import com.example.data.repository.UserRepository
import com.example.domain.model.PostItem
import com.example.domain.model.toPostItem
import javax.inject.Inject

class GetCurrentUserPostsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<PostItem> {
        val postItemDtos = userRepository.getCurrentUserPosts()
        val posts = postItemDtos?.map { it.toPostItem() } ?: emptyList()
        return posts.sortedByDescending { it.date }
    }
}