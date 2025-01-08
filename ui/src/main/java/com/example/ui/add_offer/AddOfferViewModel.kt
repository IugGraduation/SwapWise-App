package com.example.ui.add_offer

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.AddOfferUseCase
import com.example.domain.GetCategoriesNamesUseCase
import com.example.domain.PostValidationUseCase
import com.example.domain.exception.InvalidDetailsException
import com.example.domain.exception.InvalidPlaceException
import com.example.domain.exception.InvalidTitleException
import com.example.domain.model.OfferItem
import com.example.ui.add_post.IAddPostInteractions
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
class AddOfferViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stringsResource: StringsResource,
    private val getCategoriesNamesUseCase: GetCategoriesNamesUseCase,
    private val offerValidationUseCase: PostValidationUseCase,
    private val addOfferUseCase: AddOfferUseCase,
) : BaseViewModel<OfferItemUiState>(OfferItemUiState()), IAddPostInteractions {

    private val args = AddOfferArgs(savedStateHandle)

    init {
        viewModelScope.launch {
            prepareChipsList()
        }
    }

    private fun prepareChipsList() {
        tryToExecute(
            call = {  getCategoriesNamesUseCase() },
            onSuccess = ::onGetChipsDataSuccess,
        )
    }

    private fun onGetChipsDataSuccess(categoriesNames: List<String>) {
        val chipsList = List(categoriesNames.size) { index ->
            Chip(text = categoriesNames[index], selected = false, onClick = ::onCategoryChange)
        }
        _state.update {
            it.copy(chipsList = chipsList)
        }
    }


    private fun updatePostItem(update: OfferItem.() -> OfferItem) {
        _state.update {
            it.copy(offerItem = it.offerItem.update())
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

    override fun onTitleChange(title: String) {
        updateFieldError()
        updatePostItem { copy(title = title) }
    }

    override fun onDetailsChange(details: String) {
        updateFieldError()
        updatePostItem { copy(details = details) }
    }

    override fun onPlaceChange(place: String) {
        updateFieldError()
        updatePostItem { copy(place = place) }
    }

    override fun onSelectedImageChange(selectedImageUri: Uri) {
        updatePostItem { copy(imageLink = selectedImageUri.toString()) }
    }

    fun onCategoryChange(category: String) {
        updateFieldError()
        updatePostItem { copy(category = category) }
    }


    override fun onClickAdd() {
        isActionLoading()
        tryToExecute(
            call = {
                offerValidationUseCase(
                    title = state.value.offerItem.title,
                    place = state.value.offerItem.place,
                    details = state.value.offerItem.details
                )
                addOfferUseCase(args.postId, state.value.offerItem)
            },
            onSuccess = ::onActionSuccess,
            onError = ::onAddPostFail
        )
    }


    private fun onAddPostFail(throwable: Throwable) {
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
}