package com.sam.ui.search

interface ISearchInteractions {
    fun onSearchChange(newValue: String)
    fun onClickTryAgain()
    fun navigateToPostDetails(postId: String)
    fun onRetryCategories()
    fun onRetryLocations()
}