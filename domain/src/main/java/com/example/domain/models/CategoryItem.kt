package com.example.domain.models

data class CategoryItem(
    override val title: String,
    override val imgResId: Int,
    override val imgContentDescription: String = "",
): TopicItem
