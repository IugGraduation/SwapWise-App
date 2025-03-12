package com.example.ui.search

import com.example.domain.model.PostItem

interface ISearchInteractions {
    fun onSearchChange(newValue: String)
    fun onClickTryAgain()
    fun navigateToPostDetails(postItem: PostItem)

}