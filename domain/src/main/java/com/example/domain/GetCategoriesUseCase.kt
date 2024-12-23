package com.example.domain

import com.example.domain.model.CategoryItem

class GetCategoriesUseCase{
    operator fun invoke(): List<CategoryItem> {
        val categoryItem = CategoryItem(
            "food_and_beverages",
            R.drawable.img_food_and_beverages.toString(),
        )
        return listOf(categoryItem, categoryItem, categoryItem, categoryItem, categoryItem, categoryItem, categoryItem)
    }
}
