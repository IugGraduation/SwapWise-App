package com.example.domain.post

import com.example.data.repository.PostRepository
import com.example.domain.category.GetFakeCategoriesUseCase
import com.example.domain.exception.EmptyDataException
import com.example.domain.model.CategoryItem
import com.example.domain.model.PostItem
import com.example.domain.model.User
import com.example.domain.offer.GetFakeOfferDetailsUseCase
import javax.inject.Inject

class GetPostDetailsUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: String): PostItem {
        return PostItem.fromPostItemDto(
            postRepository.getPostDetails(postId) ?: throw EmptyDataException()
        )
    }
}

class GetFakePostDetailsUseCase{
    operator fun invoke(): PostItem {
        val postItem = PostItem(
            user = User(
                name = "Cameron Williamson",
                phone = "1231231231"
            ),
            name = "10kg of Sugar Up for 10kg of Rice",
            details = "Looking for a sweet deal? I have 10 kilograms of high-quality sugar that I’d like to exchange for something useful like 10 kilograms of high-quality salt or 10 kilograms of high-quality rice or 10 kilograms of high-quality anything else that I’d like to exchange for something useful",
            categoryItem = CategoryItem("Category"),
            date = "Wed, Nov 20",
            offers = List(7) { GetFakeOfferDetailsUseCase()() },
            favoriteCategoryItems = GetFakeCategoriesUseCase()().toMutableList(),
            rate = 4.8f,
        )
        return postItem
    }
}