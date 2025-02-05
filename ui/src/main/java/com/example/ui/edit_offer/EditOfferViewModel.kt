package com.example.ui.edit_offer

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteOfferUseCase
import com.example.domain.EditOfferUseCase
import com.example.domain.GetCategoriesNamesUseCase
import com.example.domain.GetOfferDetailsUseCase
import com.example.domain.PostValidationUseCase
import com.example.domain.exception.InvalidDetailsException
import com.example.domain.exception.InvalidPlaceException
import com.example.domain.exception.InvalidTitleException
import com.example.domain.model.OfferItem
import com.example.ui.base.BaseViewModel
import com.example.ui.base.StringsResource
import com.example.ui.models.Chip
import com.example.ui.models.OfferItemUiState
import com.example.ui.models.PostErrorUiState
import com.example.ui.util.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditOfferViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stringsResource: StringsResource,
    private val getOfferDetailsUseCase: GetOfferDetailsUseCase,
    private val getCategoriesNamesUseCase: GetCategoriesNamesUseCase,
    private val offerValidationUseCase: PostValidationUseCase,
    private val editOfferUseCase: EditOfferUseCase,
    private val deleteOfferUseCase: DeleteOfferUseCase,
) : BaseViewModel<OfferItemUiState>(OfferItemUiState()), IEditOfferInteractions {
    private val args = EditOfferArgs(savedStateHandle)


    init {
        viewModelScope.launch {
            getOfferDetails()
            prepareChipsList()
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


    private fun prepareChipsList() {
        tryToExecute(
            call = { getCategoriesNamesUseCase() },
            onSuccess = ::onGetChipsDataSuccess,
        )
    }

    private fun onGetChipsDataSuccess(categoriesNames: List<String>) {
        val chipsList = List(categoriesNames.size) { index ->
            Chip(
                text = categoriesNames[index], selected = false, onClick = ::onCategoryChange
            )
        }
        _state.update {
            it.copy(chipsList = chipsList)
        }
    }


    private fun updateFieldError(
        titleError: String = String.empty(),
        placeError: String = String.empty(),
        detailsError: String = String.empty(),
    ) {
        _state.update {
            it.copy(
                offerError = PostErrorUiState(
                    titleError = titleError,
                    placeError = placeError,
                    detailsError = detailsError,
                )
            )
        }
    }

    private fun updateOfferItem(update: OfferItem.() -> OfferItem) {
        _state.update {
            it.copy(offerItem = it.offerItem.update())
        }
    }

    override fun onTitleChange(title: String) {
        updateFieldError()
        updateOfferItem { copy(title = title) }
    }

    override fun onDetailsChange(details: String) {
        updateFieldError()
        updateOfferItem { copy(details = details) }
    }

    override fun onPlaceChange(place: String) {
        updateFieldError()
        updateOfferItem { copy(place = place) }
    }

    override fun onSelectedImageChange(selectedImageUri: Uri) {
        updateOfferItem { copy(imageLink = selectedImageUri.toString()) }
    }

    fun onCategoryChange(category: String) {
        updateFieldError()
        updateOfferItem { copy(category = category) }
    }


    override fun onClickSave() {
        tryToExecute(
            call = {
                offerValidationUseCase(
                    title = state.value.offerItem.title,
                    place = state.value.offerItem.place,
                    details = state.value.offerItem.details
                )
                editOfferUseCase(state.value.offerItem)
            },
            onError = ::onSaveOfferFail
        )
    }


    private fun onSaveOfferFail(throwable: Throwable) {
        when (throwable) {
            is InvalidTitleException -> {
                updateFieldError(titleError = stringsResource.invalidTitle)
            }

            is InvalidPlaceException -> {
                updateFieldError(placeError = stringsResource.invalidPlace)
            }

            is InvalidDetailsException -> {
                updateFieldError(detailsError = stringsResource.invalidDetails)
            }

            else -> onActionFail(throwable)
        }
    }


    override fun onClickDelete() {
        tryToExecute(
            call = { deleteOfferUseCase(state.value.offerItem.uuid) },
            onSuccess = { navigateUp() },
        )
    }


}