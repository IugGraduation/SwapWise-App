package com.example.ui.home

import androidx.lifecycle.ViewModel
import com.example.ui.R
import com.example.domain.models.CategoryItem
import com.example.ui.models.Orientation
import com.example.domain.models.PostItem
import com.example.domain.models.TopicItem
import com.example.ui.models.TopicType
import com.example.ui.models.TopicUiState
import com.example.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        val user = getMockUser()
        val initialTopics = listOf(
            TopicUiState(type = TopicType.Categories, items = getMockData(TopicType.Categories)),
            TopicUiState(
                type = TopicType.TopInteractive,
                items = getMockData(TopicType.TopInteractive)
            ),
            TopicUiState(
                type = TopicType.RecentPosts,
                orientation = Orientation.Vertical,
                items = getMockData(TopicType.TopInteractive)
            ),
        )
        _state.update { it.copy(user = user, topicsList = initialTopics) }
    }

    fun onNewPostFieldChange(newValue: String) {
        _state.update { it.copy(newPost = newValue) }
    }


    private fun getMockUser(): User {
        return User(
            name = "Cameron Williamson",
            imgResId = R.drawable.img_user_fake
        )
    }

    private fun getMockData(type: TopicType): List<TopicItem> {
        if (type == TopicType.Categories) {
            val categoryItem = CategoryItem(
                "food_and_beverages",
                R.drawable.img_food_and_beverages,
                ""
            )
            return listOf(categoryItem, categoryItem, categoryItem, categoryItem, categoryItem)
        } else {
            val postItem = PostItem(
                imgResId = R.drawable.img_top_interactive,
                imgContentDescription = "",
                user = User(
                    name = "Jane Cooper",
                    imgResId = R.drawable.img_user_fake,
                    imgContentDescription = ""
                ),
                title = "10kg of Sugar Up for 10kg of Rice ...",
                details = "Looking for a sweet deal? I have 10 kilograms of high-quality sugar that I’d like to exchange for something useful ...",
                onClickMakeOffer = { },
                isOpen = true,
                offersCount = 4
            )

            return listOf(postItem, postItem, postItem, postItem)
        }
    }

}