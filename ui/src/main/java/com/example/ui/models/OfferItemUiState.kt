package com.example.ui.models

import com.example.domain.model.OfferItem

data class OfferItemUiState(
    val offerItem: OfferItem = OfferItem(),
    val chipsList: List<Chip> = listOf(),

    val offerError: PostErrorUiState = PostErrorUiState(),
)
