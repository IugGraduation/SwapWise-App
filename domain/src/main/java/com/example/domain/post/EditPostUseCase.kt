package com.example.domain.post

import com.example.data.repository.PostRepository
import com.example.domain.model.PostItem
import javax.inject.Inject


class EditPostUseCase @Inject constructor(
    private val validatePostUseCase: ValidatePostUseCase,
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postItem: PostItem) {
        validatePostUseCase(
            title = postItem.title,
            place = postItem.place,
            details = postItem.details
        )

        postRepository.updatePost(postItem.toPostItemDto())
    }
}
