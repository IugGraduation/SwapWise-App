package com.example.ui.see_all_topics

sealed class SeeAllTopicsEffects {
    data class NavigateToPostDetails(val postId: String) : SeeAllTopicsEffects()
    data class NavigateToSearch(val filterCategoryName: String = "") : SeeAllTopicsEffects()
    data object NavigateUp : SeeAllTopicsEffects()
}



