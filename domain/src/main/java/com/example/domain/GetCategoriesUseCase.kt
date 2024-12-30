package com.example.domain

import com.example.domain.model.CategoryItem
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor() {
    operator fun invoke(): List<CategoryItem> {
        return GetFakeCategoriesUseCase()()
    }
}


class GetFakeCategoriesUseCase{
    operator fun invoke(): List<CategoryItem> {
        val categoryItem = CategoryItem(
            title = "Food and beverages",
            image = R.drawable.img_food_and_beverages.toString(),
        )

        return listOf(
            categoryItem,
            categoryItem,
            categoryItem,
            categoryItem,
            categoryItem,
            categoryItem,
            categoryItem
        ).mapIndexed { index, item -> item.copy(title = item.title + " " + index) }
    }
}
