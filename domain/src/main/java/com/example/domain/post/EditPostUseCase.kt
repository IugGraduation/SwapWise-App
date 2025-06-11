package com.example.domain.post

import com.example.data.repository.PostRepository
import com.example.domain.model.CategoryItem
import com.example.domain.model.PostItem
import javax.inject.Inject


class EditPostUseCase @Inject constructor(
    private val validatePostUseCase: ValidatePostUseCase,
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(imageByteArray: ByteArray?, postItem: PostItem) {
        validatePostUseCase(
            title = postItem.title,
            place = postItem.place,
            details = postItem.details
        )

        postRepository.updatePost(
            imageByteArray = imageByteArray,
            name = postItem.title,
            place = postItem.place,
            details = postItem.details,
            categoryId = postItem.categoryItem.uuid,
            favoriteCategoryIds = postItem.favoriteCategoryItems.map(CategoryItem::uuid),
            postId = postItem.uuid,
            status = if (postItem.isOpen) "1" else "0"
        )
    }
}
