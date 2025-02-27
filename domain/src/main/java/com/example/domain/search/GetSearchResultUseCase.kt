package com.example.domain.search

import com.example.data.repository.PostRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.model.Chip
import com.example.domain.model.PostItem
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(
        searchValue: String, filterChipsList: List<Chip>
    ): List<PostItem> {
        val filterCategories = filterChipsList.filter { it.selected }.map { it.text }

        val result = postRepository.searchPosts(searchValue, filterCategories)
        val postItemList = result?.filterNotNull()?.map {
            PostItem.fromPostItemDto(it)
        }
        return postItemList ?: throw EmptyDataException()
    }
}
