package com.example.ui.add_offer

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.domain.OfferValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddOfferViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val offerValidationUseCase: OfferValidationUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(OfferUiState())
    val state = _state.asStateFlow()

    val args = AddOfferArgs(savedStateHandle)

    fun onTitleChange(title: String) {
        _state.update { it.copy(title = title, titleError = null) }
    }

    fun onDetailsChange(details: String) {
        _state.update { it.copy(details = details, detailsError = null) }
    }

    fun onPlaceChange(place: String) {
        _state.update { it.copy(place = place, placeError = null) }
    }

    fun onSelectedImageChange(selectedImageUri: Uri) {
        _state.update { it.copy(selectedImageUri = selectedImageUri) }
    }

    fun onClickAddOffer() {
        if (validateForm()) {
            args.postId //add offer to this post
        }
    }

    private fun validateForm(): Boolean {
        val offerState = offerValidationUseCase(state.value.toOfferItem())
        _state.value = OfferUiState.fromIOffer(offerState)
        return offerState.isSuccess()
    }

    fun onClickAddImage() {

    }


}