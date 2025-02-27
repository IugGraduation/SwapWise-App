package com.example.domain.category

import com.example.domain.model.CategoryItem
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor() {
    suspend operator fun invoke(): List<CategoryItem> {
        return GetFakeCategoriesUseCase()()
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
