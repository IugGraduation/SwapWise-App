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
import com.example.domain.model.OfferItem
import com.example.domain.model.State
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
    private val _state = MutableStateFlow(OfferItem())
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
                is State.Loading -> OfferItem(isLoading = true)
                is State.Success -> state.data
                is State.Error -> OfferItem(error = state.message)
            }
        }
    }

    private suspend fun getAllCategories() {
        _state.update { it.copy(isLoading = true) }
        _state.update { it.copy(allCategories = getCategoriesNamesUseCase(), isLoading = false) }
    }


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
        _state.update { it.copy(image = selectedImageUri.toString()) }
    }

    fun onCategoryChange(category: String) {
        _state.update { it.copy(category = category, categoryError = null) }
    }


    fun onClickSave() {
        viewModelScope.launch {
            if (validateForm()) {
                editOfferUseCase(state.value).collect { apiState ->
                    when (apiState) {
                        is State.Error -> _state.update { it.copy(error = apiState.message) }
                        State.Loading -> _state.update { it.copy(isLoading = true) }
                        is State.Success -> {
                            _state.update { it.copy(isLoading = false) }
                            //todo: change page and go back
                        }
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val newOfferState = offerValidationUseCase(state.value)
        _state.value = newOfferState
        return newOfferState.isSuccess()
    }


    fun onClickDelete() {
        viewModelScope.launch {
            deleteOfferUseCase(state.value.uuid).collect { apiState ->
                when (apiState) {
                    is State.Error -> _state.update { it.copy(error = apiState.message) }
                    State.Loading -> _state.update { it.copy(isLoading = true) }
                    is State.Success -> {
                        _state.update { it.copy(isLoading = false) }
                        //todo: change page and go back
                    }
                }
            }
        }
    }


}