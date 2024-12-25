package com.example.ui.models

import com.example.domain.model.CategoryItem

data class Chip(val text: String, val onClick: (String) -> Unit, val selected: Boolean) {
    companion object{
        fun fromCategories(categories: List<CategoryItem>, selected: Boolean): List<Chip> {
            return categories.map { Chip(text = it.title, onClick = {}, selected = selected) }
        }
    }
}
