package com.example.ui.offer_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetOfferDetailsUseCase
import com.example.domain.model.State
import com.example.ui.models.OfferItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getOfferDetailsUseCase: GetOfferDetailsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(OfferItemUiState())
    val state = _state.asStateFlow()

    val args = OfferDetailsArgs(savedStateHandle)


    init {
        viewModelScope.launch {
            getOfferDetailsUseCase(args.offerId).collect { state ->
                _state.value = when (state) {
                    is State.Loading -> OfferItemUiState(isLoading = true)
                    is State.Success -> OfferItemUiState(offerItem = state.data)
                    is State.Error -> OfferItemUiState(error = state.message)
                }
            }
        }
    }

}