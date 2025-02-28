package com.example.ui.offer_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.model.OfferItem
import com.example.domain.offer.GetOfferDetailsUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.MyUiState
import com.example.ui.models.OfferItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getOfferDetailsUseCase: GetOfferDetailsUseCase,
) : BaseViewModel<OfferItemUiState, OfferDetailsEffects>(OfferItemUiState()),
    OfferDetailsInteractions {
    private val args = OfferDetailsArgs(savedStateHandle)

    override fun navigateUp() {
        sendUiEffect(OfferDetailsEffects.NavigateUp)
    }

    override fun navigateToPhone() {
        sendUiEffect(OfferDetailsEffects.NavigateToPhone)
    }

    override fun navigateToWhatsapp() {
        sendUiEffect(OfferDetailsEffects.NavigateToWhatsapp)
    }

    override fun navigateToMessages() {
        sendUiEffect(OfferDetailsEffects.NavigateToMessages)
    }


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
        _state.value = MyUiState(OfferItemUiState(offerItem = data))
    }
}