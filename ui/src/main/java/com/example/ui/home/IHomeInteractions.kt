package com.example.ui.home

interface IHomeInteractions {
    fun onNewPostFieldChange (newValue: String)
    fun navigateToAddPost(postTitle: String)
}