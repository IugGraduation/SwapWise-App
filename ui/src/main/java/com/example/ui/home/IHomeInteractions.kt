package com.example.ui.home

import com.example.domain.model.TopicItem

interface IHomeInteractions {
    fun onNewPostFieldChange (newValue: String)
    fun navigateToAddPost(postTitle: String = "")
    fun onClickGoToDetails(topicItem: TopicItem)
}