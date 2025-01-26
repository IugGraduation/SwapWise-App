package com.example.ui.edit_post

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeletePostUseCase
import com.example.domain.EditPostUseCase
import com.example.domain.GetCategoriesNamesUseCase
import com.example.domain.GetPostDetailsUseCase
import com.example.domain.IOfferValidationUseCase
import com.example.domain.model.PostItem
import com.example.domain.model.State
import com.example.ui.models.Chip
import com.example.ui.models.PostItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostDetailsUseCase: GetPostDetailsUseCase,
    private val getCategoriesNamesUseCase: GetCategoriesNamesUseCase,
    private val postValidationUseCase: IOfferValidationUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel(), IEditPostInteractions {
    private val _state = MutableStateFlow(PostItemUiState())
    val state = _state.asStateFlow()

    val args = EditPostArgs(savedStateHandle)


    init {
        viewModelScope.launch {
            getOfferDetails()
            prepareChipsList()
        }
    }

    private suspend fun getOfferDetails() {
        getPostDetailsUseCase(args.postId).collect { state ->
            _state.value = when (state) {
                is State.Loading -> PostItemUiState(isLoading = true)
                is State.Success -> PostItemUiState(postItem = state.data)
                is State.Error -> PostItemUiState(error = state.message)
            }
        }
    }


    private suspend fun prepareChipsList() {
        val categoriesNames = getCategoriesNamesUseCase()
        val chipsList = List(categoriesNames.size) { index ->
            Chip(
                text = categoriesNames[index],
                selected = false,
                onClick = ::onCategoryChange
            )
        }
        val favoriteChipsList = chipsList.map { it.copy() }.onEach {
            it.onClick = ::onFavoriteCategoryChange
        }
        _state.update {
            it.copy(chipsList = chipsList, favoriteChipsList = favoriteChipsList)
        }
    }


    override fun onTitleChange(title: String) {
        _state.update {
            it.copy(
                postItem = _state.value.postItem.copy(
                    title = title, titleError = null
                )
            )
        }
    }

    override fun onDetailsChange(details: String) {
        _state.update {
            it.copy(
                postItem = _state.value.postItem.copy(
                    details = details, detailsError = null
                )
            )
        }
    }

    override fun onIsOpenChange(isOpen: Boolean) {
        _state.update {
            it.copy(postItem = _state.value.postItem.copy(isOpen = isOpen))
        }
    }

    override fun onPlaceChange(place: String) {
        _state.update {
            it.copy(
                postItem = _state.value.postItem.copy(
                    place = place, placeError = null
                )
            )
        }
    }

    override fun onSelectedImageChange(selectedImageUri: Uri) {
        _state.update { it.copy(postItem = _state.value.postItem.copy(image = selectedImageUri.toString())) }
    }

    fun onCategoryChange(category: String) {
        _state.update {
            it.copy(
                postItem = _state.value.postItem.copy(
                    category = category, categoryError = null
                )
            )
        }
    }

    fun onFavoriteCategoryChange(category: String) {
        val newFavoriteChipList =
            if (state.value.postItem.favoriteCategories.contains(category)) {
                _state.value.postItem.favoriteCategories - category
            } else {
                _state.value.postItem.favoriteCategories + category
            }
        _state.update {
            it.copy(
                postItem = it.postItem.copy(
                    favoriteCategories = newFavoriteChipList.toMutableList()
                )
            )
        }
    }


    override fun onClickSave() {
        viewModelScope.launch {
            if (validateForm()) {
                editPostUseCase(state.value.postItem).collect { apiState ->
                    when (apiState) {
                        is State.Error -> _state.update { it.copy(error = apiState.message) }
                        State.Loading -> _state.update { it.copy(isLoading = true) }
                        is State.Success -> {
                            _state.update { it.copy(isLoading = false, shouldNavigateUp = true) }
                        }
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val newOfferState = postValidationUseCase(state.value.postItem)
        _state.value = PostItemUiState(postItem = newOfferState as PostItem)
        return newOfferState.isSuccess()
    }


    override fun onClickDelete() {
        viewModelScope.launch {
            deletePostUseCase(state.value.postItem.uuid).collect { apiState ->
                when (apiState) {
                    is State.Error -> _state.update { it.copy(error = apiState.message) }
                    State.Loading -> _state.update { it.copy(isLoading = true) }
                    is State.Success -> {
                        _state.update { it.copy(isLoading = false, shouldNavigateUp = true) }
                    }
                }
            }
        }
    }


}