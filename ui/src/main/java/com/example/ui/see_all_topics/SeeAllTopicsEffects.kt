package com.example.ui.see_all_topics

sealed class SeeAllTopicsEffects {
    data class NavigateToPostDetails(val postId: String) : SeeAllTopicsEffects()
    data class NavigateToEditPost(val postId: String) : SeeAllTopicsEffects()
    data class NavigateSeeAllTopics(val categoryId: String = "", val categoryTitle: String = "") :
        SeeAllTopicsEffects()
    data object NavigateUp : SeeAllTopicsEffects()
}



