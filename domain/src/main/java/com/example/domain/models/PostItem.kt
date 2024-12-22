package com.example.domain.models

import com.example.domain.GetUserUseCase


data class PostItem(
    override val imgResId: Int? = 0,
    override val imgContentDescription: String = "",
    override val user: User = GetUserUseCase()(),
    override val title: String = "",
    override val place: String = "",
    override val details: String = "",
    override val category: String = "",
    val onClickMakeOffer: () -> Unit = {},
    var onClickGoToDetails: (() -> Unit)? = null,
    val date: String = "",
    val favoriteCategories: List<CategoryItem> = listOf(),
    val isOpen: Boolean = true,
    val rate: Float = 0f,
    val offers: List<IOffer> = listOf(),


    override val imgResIdError: String? = null,
    override val imgContentDescriptionError: String? = null,
    override val titleError: String? = null,
    override val placeError: String? = null,
    override val detailsError: String? = null,
    override val categoryError: String? = null,
) : IOffer


