package com.example.domain

import com.example.data.repository.PostRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.model.PostItem
import javax.inject.Inject


class EditPostUseCase @Inject constructor(
    private val validatePostUseCase: ValidatePostUseCase,
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postItem: PostItem): Boolean {
        validatePostUseCase(
            title = postItem.title,
            place = postItem.place,
            details = postItem.details
        )
        return postRepository.updatePost(postItem.toPostItemDto()) ?: throw EmptyDataException()
    }
}
