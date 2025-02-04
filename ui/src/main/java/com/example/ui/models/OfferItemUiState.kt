package com.example.ui.models

import com.example.domain.model.OfferItem
import com.example.domain.model.UiState
import com.example.ui.base.BaseUiState

data class OfferItemUiState(
    val offerItem: OfferItem = OfferItem(),
    val shouldNavigateUp: Boolean = false,
    val chipsList: List<Chip> = listOf(),

    val offerError: PostErrorUiState = PostErrorUiState(),
    val baseUiState: BaseUiState = BaseUiState(),

    override val isLoading: Boolean = false,
    override val error: String? = null,
) : UiState
