package com.example.ui.models

import com.example.domain.model.OfferItem
import com.example.domain.model.UiState

data class OfferItemUiState(
    val offerItem: OfferItem = OfferItem(),
    val shouldNavigateUp: Boolean = false,
    val chipsList: List<Chip> = listOf(),

    override val isLoading: Boolean = false,
    override val error: String? = null,
) : UiState
