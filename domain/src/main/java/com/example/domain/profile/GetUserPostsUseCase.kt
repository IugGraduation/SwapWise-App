package com.example.domain.profile

import com.example.data.repository.UserRepository
import com.example.domain.model.PostItem
import com.example.domain.model.toPostItem
import javax.inject.Inject

class GetCurrentUserPostsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<PostItem> {
        val profilePostsDto = userRepository.getCurrentUserPosts()
        val posts = profilePostsDto?.map { it.toPostItem() } ?: emptyList()
        return posts.sortedByDescending { it.date }
    }
}