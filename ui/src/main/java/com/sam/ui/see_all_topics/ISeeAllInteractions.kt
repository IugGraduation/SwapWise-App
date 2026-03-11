package com.sam.ui.see_all_topics

import com.sam.domain.model.TopicItem
import com.sam.ui.base.INavigateUp

interface ISeeAllInteractions : INavigateUp {
    fun onClickGoToDetails(topicItem: TopicItem)
}