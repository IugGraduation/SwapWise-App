package com.example.domain

import com.example.data.repository.PostRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.model.PostItem
import javax.inject.Inject


class AddPostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postItem: PostItem): Boolean {
            return postRepository.addPost(postItem.toPostItemDto()) ?: throw EmptyDataException()
    }
}
