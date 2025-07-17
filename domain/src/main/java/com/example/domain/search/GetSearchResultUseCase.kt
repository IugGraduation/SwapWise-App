package com.example.domain.search

import com.example.data.repository.SearchRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.model.Chip
import com.example.domain.model.PostItem
import com.example.domain.model.toPostItem
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(
        searchValue: String, filterChipsList: List<Chip>
    ): List<PostItem> {
        val filterCategoryIds = if (filterChipsList.isEmpty()) null
        else {
            filterChipsList.filter { it.selected }
                .map { it.categoryItem.id }
        }
        val result = searchRepository.search(searchValue, filterCategoryIds)
        val postItemList = result?.map {
            it.toPostItem()
        }
        return postItemList ?: throw EmptyDataException()
    }
}
