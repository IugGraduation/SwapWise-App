package com.example.ui.topic_see_all

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.domain.GetCategoriesNamesUseCase
import com.example.domain.GetCategoriesUseCase
import com.example.domain.GetFakePostsUseCase
import com.example.domain.GetOffersUseCase
import com.example.domain.GetPostsUseCase
import com.example.domain.model.TopicItem
import com.example.ui.models.TopicType
import com.example.ui.models.TopicUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TopicSeeAllViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(TopicUiState())
    val state = _state.asStateFlow()

    private val args = TopicSeeAllArgs(savedStateHandle)

    init {
        var type: TopicType = TopicType.Categories
        var items: List<TopicItem> = listOf()
        when (args.topicType) {
            TopicType.Categories.name -> {
                items = GetCategoriesUseCase()()
            }
            TopicType.TopInteractive.name -> {
                type = TopicType.TopInteractive
                items = GetFakePostsUseCase()()
            }
            TopicType.RecentPosts.name -> {
                type = TopicType.RecentPosts
                items = GetFakePostsUseCase()()
            }
        }

        _state.update { it.copy(type = type, items = items) }
    }

}