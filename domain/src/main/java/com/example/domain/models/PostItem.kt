package com.example.domain.models

import com.example.domain.GetUserUseCase


data class PostItem(
    override val imgResId: Int = 0,
    override val imgContentDescription: String = "",
    override val user: User = GetUserUseCase()(),
    override val title: String = "",
    override val details: String = "",
    val onClickMakeOffer: () -> Unit = {},
    var onClickGoToDetails: (() -> Unit)? = null,
    val date: String = "",
    val favoriteCategories: List<CategoryItem> = listOf(),
    val isOpen: Boolean = true,
    val rate: Float = 0f,
    val offers: List<OfferItem> = listOf()
) : OfferItem(imgResId, imgContentDescription, user, title, details)


