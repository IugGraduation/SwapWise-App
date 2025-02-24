package com.example.ui.models

import com.example.domain.model.Chip


data class ChipUiState(
    val text: String,
    var onClick: (String) -> Unit = {},
    var selected: Boolean = false,
    val clickable: Boolean = true
){
    fun toChip(): Chip = Chip(text, selected)
}

