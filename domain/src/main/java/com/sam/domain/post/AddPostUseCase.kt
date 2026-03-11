package com.sam.domain.post

import com.sam.data.repository.PostRepository
import com.sam.domain.model.CategoryItem
import com.sam.domain.model.PostItem
import javax.inject.Inject


class AddPostUseCase @Inject constructor(
    private val validatePostUseCase: ValidatePostUseCase,
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(imageByteArray: ByteArray, postItem: PostItem) {
        validatePostUseCase(
            title = postItem.name,
            locationId = postItem.locationItem.id,
            details = postItem.details,
            categoryId = postItem.categoryItem.id
        )

        postRepository.addPost(
            imageByteArray = imageByteArray,
            name = postItem.name,
            locationId = postItem.locationItem.id,
            details = postItem.details,
            categoryId = postItem.categoryItem.id,
            favoriteCategoryIds = postItem.favoriteCategoryItems.map(CategoryItem::id)
        )
    }

}
