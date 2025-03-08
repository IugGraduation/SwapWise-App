package com.example.ui.see_all_topics

import com.example.ui.base.INavigateUp

interface ISeeAllInteractions : INavigateUp {
    fun onClickGoToDetails(topicId: String, isCategory: Boolean)
}