package com.sam.domain.search

import com.sam.data.repository.SearchRepository
import com.sam.domain.model.PostItem
import com.sam.domain.model.toPostItem
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(
        searchValue: String, categoryIdsFilter: List<String>, locationIdsFilter: List<String>
    ): List<PostItem> {
        val result = searchRepository.search(searchValue, categoryIdsFilter, locationIdsFilter)
        val postItemList = result.map {
            it.toPostItem()
        }
        return postItemList
    }
}
