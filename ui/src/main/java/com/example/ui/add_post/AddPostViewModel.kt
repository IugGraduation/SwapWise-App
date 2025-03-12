package com.example.ui.add_post

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
import com.example.domain.model.PostItem
import com.example.domain.post.AddPostUseCase
import com.example.domain.post.GetImageRequestBodyUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.NavigateUpEffect
import com.example.ui.base.StringsResource
import com.example.ui.models.ChipUiState
import com.example.ui.models.PostErrorUiState
import com.example.ui.models.PostItemUiState
import com.example.ui.util.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stringsResource: StringsResource,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addPostUseCase: AddPostUseCase,
    private val getImageRequestBodyUseCase: GetImageRequestBodyUseCase,
) : BaseViewModel<PostItemUiState, NavigateUpEffect>(PostItemUiState()), IAddPostInteractions {
    private val args = AddPostArgs(savedStateHandle)

    override fun navigateUp() {
        sendUiEffect(NavigateUpEffect.NavigateUp)
    }


    init {
        updatePostItem { copy(title = args.postTitle) }
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
        val favoriteChipsList = chipsList.map {
            it.copy(
                selected = mutableStateOf(false),
                onClick = ::onFavoriteCategoryChange
            )
        }
        updateData {
            copy(chipsList = chipsList, favoriteChipsList = favoriteChipsList)
        }
    }


    private fun updatePostItem(update: PostItem.() -> PostItem) {
        updateData {
            copy(postItem = postItem.update())
        }
    }

    private fun updateFieldError(
        titleError: String = String.empty(),
        placeError: String = String.empty(),
        detailsError: String = String.empty(),
    ) {
        updateData {
            copy(
                postError = PostErrorUiState(
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

    fun onCategoryChange(categoryItem: CategoryItem) {
        updateFieldError()
        updatePostItem { copy(categoryItem = categoryItem) }
    }

    fun onFavoriteCategoryChange(categoryItem: CategoryItem) {
        val newFavoriteChipList =
            if (state.value.data.postItem.favoriteCategoryItems.contains(categoryItem)) {
                _state.value.data.postItem.favoriteCategoryItems - categoryItem
        } else {
                _state.value.data.postItem.favoriteCategoryItems + categoryItem
        }

        updatePostItem { copy(favoriteCategoryItems = newFavoriteChipList.toMutableList()) }
    }


    override fun onClickAdd() {
        tryToExecute(
            call = {
                addPostUseCase(
                    postItem = state.value.data.postItem,
                    imageRequestBody = getImageRequestBodyUseCase(state.value.data.postItem.imageLink.toUri())!!
                )
            },
            onSuccess = { navigateUp() },
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

            is EmptyImageException -> {
                onActionFail(Exception(stringsResource.emptyImageMessage))
            }

            else -> onActionFail(throwable)
        }
    }

}