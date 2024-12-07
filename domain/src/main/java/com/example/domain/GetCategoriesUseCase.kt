package com.example.domain

import com.example.domain.models.CategoryItem

class GetCategoriesUseCase{
    operator fun invoke(): List<CategoryItem> {
        val categoryItem = CategoryItem(
            "food_and_beverages",
            R.drawable.img_food_and_beverages,
        )
        return listOf(categoryItem, categoryItem, categoryItem, categoryItem, categoryItem, categoryItem, categoryItem)
    }
}
