package com.example.ui.home


sealed class HomeEffects {
    data class NavigateToAddPost(val postTitle: String) : HomeEffects()
    data class NavigateToPostDetails(val postId: String) : HomeEffects()
    data class NavigateToEditPost(val postId: String) : HomeEffects()
    data class NavigateSeeAllTopics(val categoryId: String = "", val categoryTitle: String = "") :
        HomeEffects()
}

