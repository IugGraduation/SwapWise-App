package com.example.ui.edit_post

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.domain.category.GetCategoriesUseCase
import com.example.domain.exception.InvalidDetailsException
import com.example.domain.exception.InvalidPlaceException
import com.example.domain.exception.InvalidTitleException
import com.example.domain.location.GetLocationsUseCase
import com.example.domain.model.CategoryItem
import com.example.domain.model.LocationItem
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
    private val getLocationsUseCase: GetLocationsUseCase,
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
        getLocations()
        getCategories()
    }

    private fun getLocations() {
        updateData { copy(locationDropdown = locationDropdown.copy(isLoading = true, error = null)) }
        tryToExecute(
            call = { getLocationsUseCase() },
            shouldLoad = false,
            onSuccess = { locations ->
                val selectedId = state.value.data.postItem.locationItem.id
                val fullLocation = locations.find { it.id == selectedId }

                updateData {
                    copy(
                        locationDropdown = locationDropdown.copy(
                            items = locations,
                            selectedItem = fullLocation ?: postItem.locationItem,
                            isLoading = false
                        )
                    )
                }
            },
            onError = { throwable ->
                updateData {
                    copy(
                        locationDropdown = locationDropdown.copy(
                            isLoading = false,
                            error = throwable.message
                        )
                    )
                }
            }
        )
    }

    private fun getCategories() {
        updateData {
            copy(
                categories = categories.copy(isLoading = true, error = null),
                favoriteCategories = favoriteCategories.copy(isLoading = true, error = null)
            )
        }
        tryToExecute(
            call = { getCategoriesUseCase() },
            shouldLoad = false,
            onSuccess = { categoryItems ->
                val chipsList = categoryItems.map { category ->
                    ChipUiState(
                        categoryItem = category,
                        selected = mutableStateOf(category.id == state.value.data.postItem.categoryItem.id),
                        onClick = ::onCategoryChange
                    )
                }
                val favoriteChipsList = chipsList.map {
                    it.copy(
                        selected = mutableStateOf(state.value.data.postItem.favoriteCategoryItems.contains(it.categoryItem)),
                        onClick = ::onFavoriteCategoryChange
                    )
                }
                updateData {
                    copy(
                        categories = categories.copy(items = chipsList, isLoading = false),
                        favoriteCategories = favoriteCategories.copy(items = favoriteChipsList, isLoading = false)
                    )
                }
            },
            onError = { throwable ->
                updateData {
                    copy(
                        categories = categories.copy(isLoading = false, error = throwable.message),
                        favoriteCategories = favoriteCategories.copy(isLoading = false, error = throwable.message)
                    )
                }
            }
        )
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
                    locationError = placeError,
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

    override fun onLocationChange(location: LocationItem) {
        updateFieldError()
        updateData { copy(locationDropdown = locationDropdown.copy(selectedItem = location)) }
    }

    override fun onSelectedImageChange(selectedImageUri: Uri) {
        updatePostItem { copy(imageUrl = selectedImageUri.toString()) }
    }

    fun onCategoryChange(categoryItem: CategoryItem) {
        updateFieldError()
        state.value.data.categories.items.forEach { chip ->
            chip.selected.value = chip.categoryItem.id == categoryItem.id
        }
        updatePostItem { copy(categoryItem = categoryItem) }
    }

    fun onFavoriteCategoryChange(categoryItem: CategoryItem) {
        val favorites = state.value.data.postItem.favoriteCategoryItems
        if (favorites.contains(categoryItem)) {
            favorites.remove(categoryItem)
        } else {
            favorites.add(categoryItem)
        }
        // Visually update the specific chip's selected state
        state.value.data.favoriteCategories.items.find { it.categoryItem.id == categoryItem.id }?.let {
            it.selected.value = favorites.contains(categoryItem)
        }
    }


    override fun onClickSave(imageByteArray: ByteArray?) {
        tryToExecute(
            call = {
                editPostUseCase(
                    postItem = state.value.data.postItem.copy(
                        locationItem = state.value.data.locationDropdown.selectedItem
                            ?: LocationItem()
                    ),
                    imageByteArray = imageByteArray
                )
            },
            onSuccess = { navigateUp() },
            onError = ::onSavePostFail
        )
    }

    override fun onRetryLocations() {
        getLocations()
    }

    override fun onRetryCategories() {
        getCategories()
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

            else -> updateFieldError().also { onActionFail(throwable) }
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
