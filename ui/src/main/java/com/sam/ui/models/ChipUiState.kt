package com.sam.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.sam.domain.model.CategoryItem
import com.sam.domain.model.Chip


data class ChipUiState(
    val categoryItem: CategoryItem,
    var onClick: (CategoryItem) -> Unit = {},
    var selected: MutableState<Boolean> = mutableStateOf(false),
    val clickable: Boolean = true
){
    fun toChip(): Chip = Chip(categoryItem, selected.value)
}

