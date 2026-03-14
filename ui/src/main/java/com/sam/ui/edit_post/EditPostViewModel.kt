package com.sam.ui.edit_post

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.sam.domain.category.GetCategoriesUseCase
import com.sam.domain.exception.InvalidDetailsException
import com.sam.domain.exception.InvalidPlaceException
import com.sam.domain.exception.InvalidTitleException
import com.sam.domain.location.GetLocationsUseCase
import com.sam.domain.model.CategoryItem
import com.sam.domain.model.LocationItem
import com.sam.domain.model.PostItem
import com.sam.domain.post.DeletePostUseCase
import com.sam.domain.post.EditPostUseCase
import com.sam.domain.post.GetPostDetailsUseCase
import com.sam.ui.base.BaseViewModel
import com.sam.ui.base.StringsResource
import com.sam.ui.models.AsyncState
import com.sam.ui.models.ChipUiState
import com.sam.ui.models.PostErrorUiState
import com.sam.ui.models.PostItemUiState
import com.sam.ui.util.empty
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
) : BaseViewModel<PostItemUiState, EditPostEffects>(PostItemUiState()), IEditPostInteractions {
    private val args = EditPostArgs(savedStateHandle)

    init {
        getPostDetails()
    }

    override fun getPostDetails() {
        tryToExecuteAsync(
            call = { getPostDetailsUseCase(args.postId) },
            stateUpdater = { newState ->
                updateData { copy(postItem = newState) }
                if (newState is AsyncState.Success) {
                    onGetPostDetailsSuccess(newState.data)
                }
            }
        )
    }

    private fun onGetPostDetailsSuccess(data: PostItem) {
        updateData { copy(selectedLocation = data.locationItem) }
        getLocations()
        getCategories()
    }

    private fun getLocations() {
        tryToExecuteAsync(
            call = { getLocationsUseCase() },
            stateUpdater = { newState ->
                updateData {
                    copy(
                        locationDropdown = newState,
                        selectedLocation = if (newState is AsyncState.Success) {
                            newState.data.find { it.id == selectedLocation?.id } ?: selectedLocation
                        } else selectedLocation
                    )
                }
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
            copy(postItem = postItem.mapData { it.update() })
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
        updateData { copy(selectedLocation = location) }
    }

    override fun onSelectedImageChange(selectedImageUri: Uri) {
        updatePostItem { copy(imageUrl = selectedImageUri.toString()) }
    }

    fun onCategoryChange(categoryItem: CategoryItem) {
        updateFieldError()
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
        // Visually update the specific chip's selected state
        state.value.data.favoriteCategories.data?.find { it.categoryItem.id == categoryItem.id }?.let {
            it.selected.value = favorites.any { f -> f.id == categoryItem.id }
        }
    }


    override fun onClickSave(imageByteArray: ByteArray?) {
        val post = state.value.data.postItem.data ?: return
        tryToExecute(
            call = {
                editPostUseCase(
                    postItem = post.copy(
                        locationItem = state.value.data.selectedLocation ?: LocationItem()
                    ),
                    imageByteArray = imageByteArray
                )
            },
            onSuccess = { sendUiEffect(EditPostEffects.NavigateUp) },
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
        val postId = state.value.data.postItem.data?.id ?: return
        tryToExecute(
            call = { deletePostUseCase(postId) },
            onSuccess = { sendUiEffect(EditPostEffects.NavigateToHome) },
        )
    }


    override fun navigateUp() {
        sendUiEffect(EditPostEffects.NavigateUp)
    }


}
