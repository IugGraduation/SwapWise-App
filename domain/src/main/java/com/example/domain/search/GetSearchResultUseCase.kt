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
        val filterCategoryIds = filterChipsList.filter { it.selected }.map { it.categoryItem.uuid }

        val result = postRepository.searchPosts(searchValue, filterCategoryIds)
        val postItemList = result?.map {
            PostItem.fromPostItemDto(it)
        }
        return postItemList ?: throw EmptyDataException()
    }
}
