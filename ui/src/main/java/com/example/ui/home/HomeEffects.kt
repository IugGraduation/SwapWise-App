package com.example.ui.home


sealed class HomeEffects {
    data class NavigateToAddPost(val postTitle: String) : HomeEffects()
}



