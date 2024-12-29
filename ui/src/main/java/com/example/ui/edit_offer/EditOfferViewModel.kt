package com.example.ui.edit_offer

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteOfferUseCase
import com.example.domain.EditOfferUseCase
import com.example.domain.GetCategoriesNamesUseCase
import com.example.domain.GetOfferDetailsUseCase
import com.example.domain.OfferValidationUseCase
import com.example.domain.model.State
import com.example.ui.models.OfferItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditOfferViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getOfferDetailsUseCase: GetOfferDetailsUseCase,
    private val getCategoriesNamesUseCase: GetCategoriesNamesUseCase,
    private val offerValidationUseCase: OfferValidationUseCase,
    private val editOfferUseCase: EditOfferUseCase,
    private val deleteOfferUseCase: DeleteOfferUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(OfferItemUiState())
    val state = _state.asStateFlow()

    val args = EditOfferArgs(savedStateHandle)


    init {
        viewModelScope.launch {
            getOfferDetails()
            getAllCategories()
        }
    }

    private suspend fun getOfferDetails() {
        getOfferDetailsUseCase(args.offerId).collect { state ->
            _state.value = when (state) {
                is State.Loading -> OfferItemUiState(isLoading = true)
                is State.Success -> OfferItemUiState(offerItem = state.data)
                is State.Error -> OfferItemUiState(error = state.message)
            }
        }
    }

    private suspend fun getAllCategories() {
        _state.update { it.copy(isLoading = true) }
        _state.update {
            it.copy(
                offerItem = _state.value.offerItem.copy(allCategories = getCategoriesNamesUseCase()),
                isLoading = false
            )
        }
    }


    fun onTitleChange(title: String) {
        _state.update { it.copy(offerItem = _state.value.offerItem.copy(title = title, titleError = null)) }
    }

    fun onDetailsChange(details: String) {
        _state.update { it.copy(offerItem = _state.value.offerItem.copy(details = details, detailsError = null)) }
    }

    fun onPlaceChange(place: String) {
        _state.update { it.copy(offerItem = _state.value.offerItem.copy(place = place, placeError = null)) }
    }

    fun onSelectedImageChange(selectedImageUri: Uri) {
        _state.update { it.copy(offerItem = _state.value.offerItem.copy(image = selectedImageUri.toString())) }
    }

    fun onCategoryChange(category: String) {
        _state.update { it.copy(offerItem = _state.value.offerItem.copy(category = category, categoryError = null)) }
    }


    fun onClickSave() {
        viewModelScope.launch {
            if (validateForm()) {
                editOfferUseCase(state.value.offerItem).collect { apiState ->
                    when (apiState) {
                        is State.Error -> _state.update { it.copy(error = apiState.message) }
                        State.Loading -> _state.update { it.copy(isLoading = true) }
                        is State.Success -> {
                            _state.update { it.copy(isLoading = false, shouldNavigateUp = true) }
                        }
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val newOfferState = offerValidationUseCase(state.value.offerItem)
        _state.value =  OfferItemUiState(offerItem = newOfferState)
        return newOfferState.isSuccess()
    }


    fun onClickDelete() {
        viewModelScope.launch {
            deleteOfferUseCase(state.value.offerItem.uuid).collect { apiState ->
                when (apiState) {
                    is State.Error -> _state.update { it.copy(error = apiState.message) }
                    State.Loading -> _state.update { it.copy(isLoading = true) }
                    is State.Success -> {
                        _state.update { it.copy(isLoading = false, shouldNavigateUp = true) }
                    }
                }
            }
        }
    }


}