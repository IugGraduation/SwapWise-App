package com.example.ui.post_details

import com.example.ui.base.INavigateUp

interface PostDetailsInteractions : INavigateUp {
    fun navigateToAddOffer()
    fun navigateToOfferDetails(offerId: String)
}