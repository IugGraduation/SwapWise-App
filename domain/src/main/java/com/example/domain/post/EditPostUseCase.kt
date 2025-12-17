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
            title = postItem.name,
            locationId = postItem.locationItem.id,
            details = postItem.details,
            categoryId = postItem.categoryItem.id
        )

        postRepository.updatePost(
            imageByteArray = imageByteArray,
            name = postItem.name,
            locationId = postItem.locationItem.id,
            details = postItem.details,
            categoryId = postItem.categoryItem.id,
            favoriteCategoryIds = postItem.favoriteCategoryItems.map(CategoryItem::id),
            postId = postItem.id,
            status = if (postItem.isOpen) "Open" else "Closed"
        )
    }
}
