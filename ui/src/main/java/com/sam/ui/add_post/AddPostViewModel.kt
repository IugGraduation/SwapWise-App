package com.sam.ui.add_post

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.sam.domain.category.GetCategoriesUseCase
import com.sam.domain.exception.EmptyImageException
import com.sam.domain.exception.InvalidCategoryException
import com.sam.domain.exception.InvalidDetailsException
import com.sam.domain.exception.InvalidPlaceException
import com.sam.domain.exception.InvalidTitleException
import com.sam.domain.location.GetLocationsUseCase
import com.sam.domain.model.CategoryItem
import com.sam.domain.model.LocationItem
import com.sam.domain.model.PostItem
import com.sam.domain.post.AddPostUseCase
import com.sam.ui.base.BaseViewModel
import com.sam.ui.base.NavigateUpEffect
import com.sam.ui.base.StringsResource
import com.sam.ui.models.ChipUiState
import com.sam.ui.models.PostErrorUiState
import com.sam.ui.models.PostItemUiState
import com.sam.ui.util.checkImageNotNull
import com.sam.ui.util.empty
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
        tryToExecuteAsync(
            call = { getLocationsUseCase() },
            stateUpdater = { newState ->
                updateData { copy(locationDropdown = newState) }
            }
        )
    }

    private fun getCategories() {
        tryToExecuteAsync(
            call = { getCategoriesUseCase() },
            stateUpdater = { newState ->
                val chips = newState.mapData { categoryItems ->
                    categoryItems.map { category ->
                        ChipUiState(
                            categoryItem = category,
                            selected = mutableStateOf(category.id == state.value.data.postItem.data?.categoryItem?.id),
                            onClick = ::onCategoryChange
                        )
                    }
                }

                val favoriteChips = newState.mapData { categoryItems ->
                    categoryItems.map { category ->
                        ChipUiState(
                            categoryItem = category,
                            selected = mutableStateOf(state.value.data.postItem.data?.favoriteCategoryItems?.any { it.id == category.id } == true),
                            onClick = ::onFavoriteCategoryChange
                        )
                    }
                }

                updateData {
                    copy(
                        categories = chips,
                        favoriteCategories = favoriteChips
                    )
                }
            }
        )
    }


    private fun updatePostItem(update: PostItem.() -> PostItem) {
        updateData {
            copy(postItem = postItem.mapData { it.update() })
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
        updateData { copy(selectedLocation = location) }
    }

    override fun onSelectedImageChange(selectedImageUri: Uri) {
        updatePostItem { copy(imageUrl = selectedImageUri.toString()) }
    }

    fun onCategoryChange(categoryItem: CategoryItem) {
        updateFieldError()
        // Single selection logic: unselect all, select current
        state.value.data.categories.data?.forEach { chip ->
            chip.selected.value = chip.categoryItem.id == categoryItem.id
        }
        updatePostItem { copy(categoryItem = categoryItem) }
    }

    fun onFavoriteCategoryChange(categoryItem: CategoryItem) {
        val post = state.value.data.postItem.data ?: return
        val favorites = post.favoriteCategoryItems
        if (favorites.any { it.id == categoryItem.id }) {
            favorites.removeAll { it.id == categoryItem.id }
        } else {
            favorites.add(categoryItem)
        }
        // Multi selection logic: visually toggle the chip
        state.value.data.favoriteCategories.data?.find { it.categoryItem.id == categoryItem.id }?.let {
            it.selected.value = favorites.any { it.id == categoryItem.id }
        }
    }


    override fun onClickAdd(imageByteArray: ByteArray?) {
        val post = state.value.data.postItem.data ?: return
        tryToExecute(
            call = {
                addPostUseCase(
                    postItem = post.copy(
                        locationItem = state.value.data.selectedLocation
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
