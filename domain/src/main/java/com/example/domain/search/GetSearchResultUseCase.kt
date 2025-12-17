package com.example.domain.search

import com.example.data.repository.SearchRepository
import com.example.domain.model.PostItem
import com.example.domain.model.toPostItem
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
