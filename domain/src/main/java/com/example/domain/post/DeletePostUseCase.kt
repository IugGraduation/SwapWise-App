package com.example.domain.post

import com.example.data.repository.PostRepository
import javax.inject.Inject


class DeletePostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: String) {
        postRepository.deletePost(postId)
    }
}
