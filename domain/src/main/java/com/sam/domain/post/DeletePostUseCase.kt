package com.sam.domain.post

import com.sam.data.repository.PostRepository
import javax.inject.Inject


class DeletePostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: String) {
        postRepository.deletePost(postId)
    }
}
