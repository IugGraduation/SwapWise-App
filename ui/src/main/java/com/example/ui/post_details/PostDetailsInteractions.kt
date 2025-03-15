package com.example.ui.post_details

import com.example.domain.model.OfferItem
import com.example.ui.base.INavigateUp

interface PostDetailsInteractions : INavigateUp {
    fun navigateToAddOffer()
    fun navigateToOfferDetails(offerItem: OfferItem)
    fun navigateToEditPost(postId: String)
}