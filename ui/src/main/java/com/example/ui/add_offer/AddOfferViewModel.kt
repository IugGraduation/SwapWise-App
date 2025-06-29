package com.example.ui.add_offer

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import com.example.domain.category.GetCategoriesUseCase
import com.example.domain.exception.EmptyImageException
import com.example.domain.exception.InvalidDetailsException
import com.example.domain.exception.InvalidPlaceException
import com.example.domain.exception.InvalidTitleException
import com.example.domain.model.CategoryItem
import com.example.domain.model.OfferItem
import com.example.domain.offer.AddOfferUseCase
import com.example.domain.post.GetImageRequestBodyUseCase
import com.example.ui.add_post.IAddPostInteractions
import com.example.ui.base.BaseViewModel
import com.example.ui.base.NavigateUpEffect
import com.example.ui.base.StringsResource
import com.example.ui.models.ChipUiState
import com.example.ui.models.OfferItemUiState
import com.example.ui.models.PostErrorUiState
import com.example.ui.util.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddOfferViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stringsResource: StringsResource,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getImageRequestBodyUseCase: GetImageRequestBodyUseCase,
    private val addOfferUseCase: AddOfferUseCase,
) : BaseViewModel<OfferItemUiState, NavigateUpEffect>(OfferItemUiState()), IAddPostInteractions {

    private val args = AddOfferArgs(savedStateHandle)

    override fun navigateUp() {
        sendUiEffect(NavigateUpEffect.NavigateUp)
    }


    init {
        prepareChipsList()
    }

    private fun prepareChipsList() {
        tryToExecute(
            call = { getCategoriesUseCase() },
            onSuccess = ::onGetChipsDataSuccess,
        )
    }

    private fun onGetChipsDataSuccess(categoryItems: List<CategoryItem>) {
        val chipsList = List(categoryItems.size) { index ->
            ChipUiState(
                categoryItem = categoryItems[index],
                selected = mutableStateOf(false),
                onClick = ::onCategoryChange
            )
        }
        updateData {
            copy(chipsList = chipsList)
        }
    }


    private fun updatePostItem(update: OfferItem.() -> OfferItem) {
        updateData {
            copy(offerItem = offerItem.update())
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

    override fun onTitleChange(title: String) {
        updateFieldError()
        updatePostItem { copy(name = title) }
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
        updatePostItem { copy(imageUrl = selectedImageUri.toString()) }
    }

    fun onCategoryChange(categoryItem: CategoryItem) {
        updateFieldError()
        updatePostItem { copy(categoryItem = categoryItem) }
    }


    override fun onClickAdd(imageByteArray: ByteArray?) {
        tryToExecute(
            call = {
                addOfferUseCase(
                    getImageRequestBodyUseCase(state.value.data.offerItem.imageUrl.toUri())!!,
                    args.postId,
                    state.value.data.offerItem
                )
            },
            onSuccess = { navigateUp() },
            onError = ::onAddOfferFail
        )
    }


    private fun onAddOfferFail(throwable: Throwable) {
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

            is EmptyImageException -> {
                onActionFail(Exception(stringsResource.emptyImageMessage))
            }

            else -> onActionFail(throwable)
        }
    }
}