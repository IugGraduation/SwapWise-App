package com.sam.domain.profile

import com.sam.data.repository.UserRepository
import com.sam.domain.model.PostItem
import com.sam.domain.model.toPostItem
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