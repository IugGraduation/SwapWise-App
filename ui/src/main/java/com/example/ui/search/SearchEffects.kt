package com.example.ui.search


sealed class SearchEffects {
    data class NavigateToPostDetails(val postId: String) : SearchEffects()
}



