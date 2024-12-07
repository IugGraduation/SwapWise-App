package com.example.domain.models

data class PostItem(
    override val imgResId: Int,
    override val imgContentDescription: String = "",
    val user: User,
    override val title: String,
    val details: String,
    val onClickMakeOffer: () -> Unit,
    val isOpen: Boolean = true,
    val offersCount: Int = 0,
) : TopicItem


