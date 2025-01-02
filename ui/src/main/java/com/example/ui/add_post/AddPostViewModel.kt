package com.example.ui.add_post

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.AddPostUseCase
import com.example.domain.GetCategoriesNamesUseCase
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
class AddPostViewModel @Inject constructor(
    private val getCategoriesNamesUseCase: GetCategoriesNamesUseCase,
    private val postValidationUseCase: IOfferValidationUseCase,
    private val addPostUseCase: AddPostUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(PostItemUiState())
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
            getAllCategories()
        }
    }

    private suspend fun getAllCategories() {
        val categoriesNames = getCategoriesNamesUseCase()
        _state.update {
            it.copy(chipsList = List(categoriesNames.size) { index ->
                Chip(
                    text = categoriesNames[index],
                    selected = false,
                    onClick = ::onCategoryChange
                )
            })
        }
    }


    fun onTitleChange(title: String) {
        _state.update {
            it.copy(
                postItem = _state.value.postItem.copy(
                    title = title,
                    titleError = null
                )
            )
        }
    }

    fun onDetailsChange(details: String) {
        _state.update {
            it.copy(
                postItem = _state.value.postItem.copy(
                    details = details,
                    detailsError = null
                )
            )
        }
    }

    fun onPlaceChange(place: String) {
        _state.update {
            it.copy(
                postItem = _state.value.postItem.copy(
                    place = place,
                    placeError = null
                )
            )
        }
    }

    fun onSelectedImageChange(selectedImageUri: Uri) {
        _state.update { it.copy(postItem = _state.value.postItem.copy(image = selectedImageUri.toString())) }
    }

    fun onCategoryChange(category: String) {
        _state.update {
            it.copy(
                postItem = _state.value.postItem.copy(
                    category = category,
                    categoryError = null
                )
            )
        }
    }

    fun onFavoriteCategoryChange(category: String) {
        if (state.value.postItem.favoriteCategories.contains(category)) {
            _state.value.postItem.favoriteCategories.remove(category)
        }else{
            _state.value.postItem.favoriteCategories.add(category)
        }
    }


    fun onClickAddPost() {
        viewModelScope.launch {
            if (validateForm()) {
                addPostUseCase(state.value.postItem).collect { apiState ->
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
        val newPostState = postValidationUseCase(state.value.postItem)
        _state.value = PostItemUiState(postItem = newPostState as PostItem)
        return newPostState.isSuccess()
    }


}