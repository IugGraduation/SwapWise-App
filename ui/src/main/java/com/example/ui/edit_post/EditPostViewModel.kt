package com.example.ui.edit_post

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.domain.category.GetCategoriesUseCase
import com.example.domain.exception.InvalidDetailsException
import com.example.domain.exception.InvalidPlaceException
import com.example.domain.exception.InvalidTitleException
import com.example.domain.model.CategoryItem
import com.example.domain.model.PostItem
import com.example.domain.post.DeletePostUseCase
import com.example.domain.post.EditPostUseCase
import com.example.domain.post.GetPostDetailsUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.MyUiState
import com.example.ui.base.NavigateUpEffect
import com.example.ui.base.StringsResource
import com.example.ui.models.ChipUiState
import com.example.ui.models.PostErrorUiState
import com.example.ui.models.PostItemUiState
import com.example.ui.util.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stringsResource: StringsResource,
    private val getPostDetailsUseCase: GetPostDetailsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : BaseViewModel<PostItemUiState, NavigateUpEffect>(PostItemUiState()), IEditPostInteractions {
    private val args = EditPostArgs(savedStateHandle)

    init {
        getPostDetails()
    }

    private fun getPostDetails() {
        tryToExecute(
            call = { getPostDetailsUseCase(args.postId) },
            onSuccess = ::onGetPostDetailsSuccess,
        )
    }

    private fun onGetPostDetailsSuccess(data: PostItem) {
        _state.value = MyUiState(PostItemUiState(postItem = data))
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
                selected = mutableStateOf(
                    categoryItems[index].id == state.value.data.postItem.categoryItem.id
                ),
                onClick = ::onCategoryChange
            )

        }
        val favoriteChipsList = chipsList.map {
            it.copy(
                categoryItem = it.categoryItem.copy(imageUrl = ""),
                selected = mutableStateOf(
                    state.value.data.postItem.favoriteCategoryItems.contains(it.categoryItem)
                ),
                onClick = ::onFavoriteCategoryChange
            )
        }
        updateData {
            copy(chipsList = chipsList, favoriteChipsList = favoriteChipsList)
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

    private fun updatePostItem(update: PostItem.() -> PostItem) {
        updateData {
            copy(postItem = postItem.update())
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

    override fun onIsOpenChange(isOpen: Boolean) {
        updatePostItem { copy(isOpen = isOpen) }
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

    fun onFavoriteCategoryChange(categoryItem: CategoryItem) {
        val newFavoriteChipList =
            if (state.value.data.postItem.favoriteCategoryItems.contains(categoryItem)) {
                _state.value.data.postItem.favoriteCategoryItems - categoryItem
        } else {
                _state.value.data.postItem.favoriteCategoryItems + categoryItem
        }

        updatePostItem { copy(favoriteCategoryItems = newFavoriteChipList.toMutableList()) }
    }


    override fun onClickSave(imageByteArray: ByteArray?) {
        tryToExecute(
            call = {
                editPostUseCase(
                    postItem = state.value.data.postItem,
                    imageByteArray = imageByteArray
                )
            },
            onSuccess = { navigateUp() },
            onError = ::onSavePostFail
        )
    }

    private fun onSavePostFail(throwable: Throwable) {
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
            call = { deletePostUseCase(state.value.data.postItem.id) },
            onSuccess = { navigateUp() },
        )
    }


    override fun navigateUp() {
        sendUiEffect(NavigateUpEffect.NavigateUp)
    }


}