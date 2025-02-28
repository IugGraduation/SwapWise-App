package com.example.ui.edit_offer

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.example.domain.category.GetCategoriesNamesUseCase
import com.example.domain.exception.InvalidDetailsException
import com.example.domain.exception.InvalidPlaceException
import com.example.domain.exception.InvalidTitleException
import com.example.domain.model.OfferItem
import com.example.domain.offer.DeleteOfferUseCase
import com.example.domain.offer.EditOfferUseCase
import com.example.domain.offer.GetOfferDetailsUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.MyUiState
import com.example.ui.base.NavigateUpEffect
import com.example.ui.base.StringsResource
import com.example.ui.models.ChipUiState
import com.example.ui.models.OfferItemUiState
import com.example.ui.models.PostErrorUiState
import com.example.ui.util.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditOfferViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stringsResource: StringsResource,
    private val getOfferDetailsUseCase: GetOfferDetailsUseCase,
    private val getCategoriesNamesUseCase: GetCategoriesNamesUseCase,
    private val editOfferUseCase: EditOfferUseCase,
    private val deleteOfferUseCase: DeleteOfferUseCase,
) : BaseViewModel<OfferItemUiState, NavigateUpEffect>(OfferItemUiState()), IEditOfferInteractions {
    private val args = EditOfferArgs(savedStateHandle)

    override fun navigateUp() {
        sendUiEffect(NavigateUpEffect.NavigateUp)
    }


    init {
        getOfferDetails()
    }

    private fun getOfferDetails() {
        tryToExecute(
            call = { getOfferDetailsUseCase(args.offerId) },
            onSuccess = ::onGetOfferDetailsSuccess,
        )
    }

    private fun onGetOfferDetailsSuccess(data: OfferItem) {
        _state.value = MyUiState(OfferItemUiState(offerItem = data))
        prepareChipsList()
    }


    private fun prepareChipsList() {
        tryToExecute(
            call = { getCategoriesNamesUseCase() },
            onSuccess = ::onGetChipsDataSuccess,
        )
    }

    private fun onGetChipsDataSuccess(categoriesNames: List<String>) {
        val chipsList = List(categoriesNames.size) { index ->
            ChipUiState(
                text = categoriesNames[index], selected = false, onClick = ::onCategoryChange
            )
        }
        updateData {
            copy(chipsList = chipsList)
        }
    }


    private fun updateFieldError(
        titleError: String = String.empty(),
        placeError: String = String.empty(),
        detailsError: String = String.empty(),
    ) {
        updateData {
            copy(
                offerError = PostErrorUiState(
                    titleError = titleError,
                    placeError = placeError,
                    detailsError = detailsError,
                )
            )
        }
    }

    private fun updateOfferItem(update: OfferItem.() -> OfferItem) {
        updateData {
            copy(offerItem = offerItem.update())
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
            call = { editOfferUseCase(state.value.data.offerItem) },
            onSuccess = { navigateUp() },
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
            call = { deleteOfferUseCase(state.value.data.offerItem.uuid) },
            onSuccess = { navigateUp() },
        )
    }


}