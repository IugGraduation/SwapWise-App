package com.example.domain.category

import com.example.data.repository.HomeRepository
import com.example.domain.model.CategoryItem
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): List<CategoryItem> {
        return homeRepository.getCategories()?.map {
            CategoryItem.fromPostItemDto(it)
        } ?: listOf()
    }
}


class GetFakeCategoriesUseCase {
    operator fun invoke(): List<CategoryItem> {
        val categoryItem = CategoryItem(
            name = "Food and beverages",
        )
        return listOf(
            categoryItem, categoryItem, categoryItem,
            categoryItem, categoryItem, categoryItem,
        ).mapIndexed { index, item -> item.copy(name = item.name + " " + index) }
    }
}
