package com.example.domain

import com.example.data.model.StateDto
import com.example.data.repository.PostRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DeletePostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: String):Boolean {
        return postRepository.deletePost(postId) ?: throw EmptyDataException()

    }
}
