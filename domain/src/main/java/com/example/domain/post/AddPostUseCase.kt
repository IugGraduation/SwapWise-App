package com.example.domain.post

import androidx.core.net.toUri
import com.example.data.repository.PostRepository
import com.example.domain.model.CategoryItem
import com.example.domain.model.PostItem
import javax.inject.Inject


class AddPostUseCase @Inject constructor(
    private val validatePostUseCase: ValidatePostUseCase,
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(postItem: PostItem) {
        validatePostUseCase(
            title = postItem.title,
            place = postItem.place,
            details = postItem.details
        )

        postRepository.addPost(
            image = postItem.imageLink.toUri(),
            name = postItem.title,
            place = postItem.place,
            details = postItem.details,
            categoryId = postItem.categoryItem.uuid,
            favoriteCategoryIds = postItem.favoriteCategoryItems.map(CategoryItem::uuid)
        )
    }

}
