package com.sam.ui.home

import com.sam.domain.model.TopicItem

interface IHomeInteractions {
    fun onNewPostFieldChange (newValue: String)
    fun navigateToAddPost(postTitle: String = "")
    fun onClickGoToDetails(topicItem: TopicItem)
    fun onRetryHome()
}