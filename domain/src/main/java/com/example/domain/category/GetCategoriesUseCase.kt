package com.example.domain.category

import com.example.data.repository.HomeRepository
import com.example.domain.model.CategoryItem
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): List<CategoryItem> {
         return homeRepository.seeAll("category")?.map {
           CategoryItem.fromTopicItemDto(it)
        } ?: listOf()
    }
}


class GetFakeCategoriesUseCase {
    operator fun invoke(): List<CategoryItem> {
        val categoryItem = CategoryItem(
            title = "Food and beverages",
        )
        return listOf(
            categoryItem, categoryItem, categoryItem,
            categoryItem, categoryItem, categoryItem,
        ).mapIndexed { index, item -> item.copy(title = item.title + " " + index) }
    }
}
