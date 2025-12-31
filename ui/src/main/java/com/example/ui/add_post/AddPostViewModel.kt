package com.example.ui.add_post

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.domain.category.GetCategoriesUseCase
import com.example.domain.exception.EmptyImageException
import com.example.domain.exception.InvalidCategoryException
import com.example.domain.exception.InvalidDetailsException
import com.example.domain.exception.InvalidPlaceException
import com.example.domain.exception.InvalidTitleException
import com.example.domain.location.GetLocationsUseCase
import com.example.domain.model.CategoryItem
import com.example.domain.model.LocationItem
import com.example.domain.model.PostItem
import com.example.domain.post.AddPostUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.NavigateUpEffect
import com.example.ui.base.StringsResource
import com.example.ui.models.ChipUiState
import com.example.ui.models.PostErrorUiState
import com.example.ui.models.PostItemUiState
import com.example.ui.util.checkImageNotNull
import com.example.ui.util.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stringsResource: StringsResource,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addPostUseCase: AddPostUseCase,
    private val getLocationsUseCase: GetLocationsUseCase
) : BaseViewModel<PostItemUiState, NavigateUpEffect>(PostItemUiState()), IAddPostInteractions {
    private val args = AddPostArgs(savedStateHandle)

    init {
        updatePostItem { copy(name = args.postTitle) }
        getLocations()
        getCategories()
    }

    override fun navigateUp() {
        sendUiEffect(NavigateUpEffect.NavigateUp)
    }

    private fun getLocations() {
        updateData { copy(locationDropdown = locationDropdown.copy(isLoading = true, error = null)) }
        tryToExecute(
            call = { getLocationsUseCase() },
            shouldLoad = false,
            onSuccess = { locations ->
                updateData {
                    copy(
                        locationDropdown = locationDropdown.copy(
                            items = locations,
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
                    locationError = placeError,
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

    override fun onLocationChange(location: LocationItem) {
        updateFieldError()
        updateData { copy(locationDropdown = locationDropdown.copy(selectedItem = location)) }
    }

    override fun onSelectedImageChange(selectedImageUri: Uri) {
        updatePostItem { copy(imageUrl = selectedImageUri.toString()) }
    }

    fun onCategoryChange(categoryItem: CategoryItem) {
        updateFieldError()
        // Single selection logic: unselect all, select current
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
        // Multi selection logic: visually toggle the chip
        state.value.data.favoriteCategories.items.find { it.categoryItem.id == categoryItem.id }?.let {
            it.selected.value = favorites.contains(categoryItem)
        }
    }


    override fun onClickAdd(imageByteArray: ByteArray?) {
        tryToExecute(
            call = {
                addPostUseCase(
                    postItem = state.value.data.postItem.copy(
                        locationItem = state.value.data.locationDropdown.selectedItem
                            ?: LocationItem()
                    ),
                    imageByteArray = imageByteArray.checkImageNotNull()
                )
            },
            onSuccess = { navigateUp() },
            onError = ::onAddPostFail
        )
    }

    override fun onRetryLocations() {
        getLocations()
    }

    override fun onRetryCategories() {
        getCategories()
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

            is InvalidCategoryException -> {
                onActionFail(Exception(stringsResource.invalidCategory))
            }

            else -> updateFieldError().also { onActionFail(throwable) }
        }
    }

}
