package com.example.ui.models

import com.example.domain.model.CategoryItem
import com.example.domain.model.Chip


data class ChipUiState(
    val categoryItem: CategoryItem,
    var onClick: (CategoryItem) -> Unit = {},
    var selected: Boolean = false,
    val clickable: Boolean = true
){
    fun toChip(): Chip = Chip(categoryItem, selected)
}

