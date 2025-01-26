package com.example.ui.models


data class Chip(
    val text: String,
    var onClick: (String) -> Unit = {},
    var selected: Boolean = false,
    val clickable: Boolean = true
)
