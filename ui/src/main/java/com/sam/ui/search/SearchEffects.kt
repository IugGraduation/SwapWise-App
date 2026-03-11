package com.sam.ui.search


sealed class SearchEffects {
    data class NavigateToPostDetails(val postId: String) : SearchEffects()
    data class NavigateToEditPost(val postId: String) : SearchEffects()
}



