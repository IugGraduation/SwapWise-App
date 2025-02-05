package com.example.ui.offer_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.GetOfferDetailsUseCase
import com.example.domain.model.OfferItem
import com.example.ui.base.BaseViewModel
import com.example.ui.models.OfferItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getOfferDetailsUseCase: GetOfferDetailsUseCase,
    ) : BaseViewModel<OfferItemUiState>(OfferItemUiState()) {
    private val args = OfferDetailsArgs(savedStateHandle)


    init {
        viewModelScope.launch {
            getOfferDetails()
        }
    }

    private fun getOfferDetails() {
        tryToExecute(
            call = { getOfferDetailsUseCase(args.offerId) },
            onSuccess = ::onGetOfferDetailsSuccess,
        )
    }

    private fun onGetOfferDetailsSuccess(data: OfferItem) {
        _state.value = OfferItemUiState(offerItem = data)
    }

}